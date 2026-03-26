<?php
header("Content-Type: application/json");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST, OPTIONS");
header("Access-Control-Allow-Headers: Content-Type");

if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(200);
    exit;
}

require_once "db_connect.php";

function jsonError($msg) {
    echo json_encode(["success" => false, "message" => $msg]);
    exit;
}

if ($conn === null) {
    jsonError("Database connection failed. Start XAMPP MySQL and run database_schema.sql in phpMyAdmin.");
}

$raw = file_get_contents("php://input");
$data = json_decode($raw, true);

if (!is_array($data) || !isset($data['action'])) {
    jsonError("Action required");
}

$action = $data['action'];
$num_telephone = trim($data['num_telephone'] ?? $data['phone'] ?? '');

if ($action === "register") {
    $prenom = $conn->real_escape_string(trim($data['prenom'] ?? ''));
    $nom = $conn->real_escape_string(trim($data['nom'] ?? ''));
    $genre = $conn->real_escape_string(trim($data['genre'] ?? ''));
    $age = intval($data['age'] ?? 0);

    if (empty($num_telephone)) {
        jsonError("Numéro de téléphone obligatoire");
    }

    $num_telephone = $conn->real_escape_string($num_telephone);
    $otp = str_pad(rand(0, 9999), 4, "0", STR_PAD_LEFT);

    $checkReq = $conn->query("SELECT id FROM users WHERE num_telephone='$num_telephone'");
    if (!$checkReq) {
        jsonError("Database error. Ensure 'users' table exists with column 'num_telephone'.");
    }

    if ($checkReq->num_rows > 0) {
        $ok = $conn->query("UPDATE users SET otp='$otp', prenom='$prenom', nom='$nom', genre='$genre', age=$age WHERE num_telephone='$num_telephone'");
    } else {
        $ok = $conn->query("INSERT INTO users (prenom, nom, genre, age, num_telephone, otp) VALUES ('$prenom', '$nom', '$genre', $age, '$num_telephone', '$otp')");
    }

    if (!$ok) {
        error_log("INSERT/UPDATE failed for phone: $num_telephone. Error: " . $conn->error);
        jsonError("Database write failed: " . $conn->error);
    }

    // Log successful registration/update
    error_log("User registered/updated: $num_telephone");
    echo json_encode(["success" => true, "message" => "OTP envoyé", "otp" => $otp]);

} elseif ($action === "login") {
    if (empty($num_telephone)) {
        jsonError("Numéro de téléphone obligatoire");
    }

    $num_telephone = $conn->real_escape_string($num_telephone);
    $checkReq = $conn->query("SELECT id FROM users WHERE num_telephone='$num_telephone'");

    if (!$checkReq) {
        jsonError("Database error.");
    }

    if ($checkReq->num_rows === 0) {
        jsonError("Compte non trouvé");
    }

    $otp = str_pad(rand(1000, 9999), 4, "0", STR_PAD_LEFT);
    $conn->query("UPDATE users SET otp='$otp' WHERE num_telephone='$num_telephone'");
    echo json_encode(["success" => true, "message" => "OTP envoyé", "otp" => $otp]);

} elseif ($action === "verify") {
    $code = trim($data['code'] ?? '');

    if (empty($num_telephone) || empty($code)) {
        jsonError("Code invalide");
    }

    $num_telephone = $conn->real_escape_string($num_telephone);
    $code = $conn->real_escape_string($code);

    $res = $conn->query("SELECT * FROM users WHERE num_telephone='$num_telephone' AND otp='$code'");

    if (!$res || $res->num_rows === 0) {
        jsonError("Code invalide");
    }

    $conn->query("UPDATE users SET otp=NULL WHERE num_telephone='$num_telephone'");
    $user = $res->fetch_assoc();
    unset($user['otp']);

    echo json_encode(["success" => true, "message" => "Authentifié", "user" => $user]);

} else {
    jsonError("Action invalide");
}

$conn->close();
