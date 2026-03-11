<?php
header("Content-Type: application/json");
require_once "db_connect.php";

$data = json_decode(file_get_contents("php://input"), true);

if (!isset($data['action'])) {
    echo json_encode(["success" => false, "message" => "Action required"]);
    exit;
}

$action = $data['action'];

if ($action === "register") {
    $prenom = $conn->real_escape_string($data['prenom'] ?? '');
    $nom = $conn->real_escape_string($data['nom'] ?? '');
    $genre = $conn->real_escape_string($data['genre'] ?? '');
    $age = intval($data['age'] ?? 0);
    $phone = $conn->real_escape_string($data['phone'] ?? '');

    if (empty($phone)) {
        echo json_encode(["success" => false, "message" => "Phone is required"]);
        exit;
    }

    $otp = str_pad(rand(0, 9999), 4, "0", STR_PAD_LEFT);

    // Check if phone exists
    $checkReq = $conn->query("SELECT * FROM users WHERE phone='$phone'");
    if ($checkReq->num_rows > 0) {
        $conn->query("UPDATE users SET otp='$otp', prenom='$prenom', nom='$nom', genre='$genre', age=$age WHERE phone='$phone'");
    } else {
        $conn->query("INSERT INTO users (prenom, nom, genre, age, phone, otp) VALUES ('$prenom', '$nom', '$genre', $age, '$phone', '$otp')");
    }

    echo json_encode(["success" => true, "message" => "OTP Sent", "otp" => strval($otp)]);
} elseif ($action === "login") {
    $phone = $conn->real_escape_string($data['phone'] ?? '');

    if (empty($phone)) {
        echo json_encode(["success" => false, "message" => "Phone is required"]);
        exit;
    }

    // Check if phone exists
    $checkReq = $conn->query("SELECT * FROM users WHERE phone='$phone'");
    if ($checkReq->num_rows > 0) {
        $otp = rand(1000, 9999);
        $conn->query("UPDATE users SET otp='$otp' WHERE phone='$phone'");
        echo json_encode(["success" => true, "message" => "OTP Sent", "otp" => strval($otp)]);
    } else {
        echo json_encode(["success" => false, "message" => "Account not found"]);
    }
} elseif ($action === "verify") {
    $phone = $conn->real_escape_string($data['phone'] ?? '');
    $code = $conn->real_escape_string($data['code'] ?? '');

    $res = $conn->query("SELECT * FROM users WHERE phone='$phone' AND otp='$code'");
    if ($res->num_rows > 0) {
        // Validation successful, clear OTP
        $conn->query("UPDATE users SET otp=NULL WHERE phone='$phone'");
        $user = $res->fetch_assoc();
        unset($user['otp']);
        echo json_encode(["success" => true, "message" => "Authenticated", "user" => $user]);
    } else {
        echo json_encode(["success" => false, "message" => "Invalid code"]);
    }
} else {
    echo json_encode(["success" => false, "message" => "Invalid action"]);
}

$conn->close();
?>
