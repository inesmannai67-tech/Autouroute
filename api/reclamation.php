<?php
header("Content-Type: application/json");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST, GET, OPTIONS");
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

function jsonSuccess($msg, $data = []) {
    echo json_encode(array_merge(["success" => true, "message" => $msg], $data));
    exit;
}

if ($conn === null) {
    jsonError("Database connection failed.");
}

$method = $_SERVER['REQUEST_METHOD'];

// -----------------------
// POST: submit complaint
// -----------------------
if ($method === 'POST') {
    $raw = file_get_contents("php://input");
    $data = json_decode($raw, true);

    if (!is_array($data)) {
        jsonError("Invalid JSON body");
    }

    // Expected keys from Android ApiClient:
    // user_phone, user_name (optional), type, category, message, latitude, longitude
    $user_phone = trim($data['user_phone'] ?? '');
    $type = trim($data['type'] ?? '');
    $category_name = trim($data['category'] ?? '');
    $message = trim($data['message'] ?? '');
    $latitude = floatval($data['latitude'] ?? 0);
    $longitude = floatval($data['longitude'] ?? 0);

    if ($user_phone === '') {
        jsonError("user_phone obligatoire");
    }
    if ($type === '' || $category_name === '') {
        jsonError("Type et catégorie obligatoires");
    }
    if ($latitude == 0 && $longitude == 0) {
        jsonError("Coordonnées GPS obligatoires");
    }
    if ($message === '') {
        // complaints.description is NOT NULL in your schema
        $message = $type . " - " . $category_name;
    }

    // 1) user_id from users.num_telephone
    $user_id = null;
    $stmt = $conn->prepare("SELECT id FROM users WHERE num_telephone = ? LIMIT 1");
    if (!$stmt) {
        jsonError("Prepare failed (users): " . $conn->error);
    }
    $stmt->bind_param("s", $user_phone);
    $stmt->execute();
    $stmt->store_result();
    if ($stmt->num_rows === 0) {
        $stmt->close();
        jsonError("Utilisateur introuvable pour ce numéro");
    }
    $stmt->bind_result($user_id);
    $stmt->fetch();
    $stmt->close();

    // 2) category_id from categories.name (create category if missing)
    $category_id = null;
    $stmt = $conn->prepare("SELECT id FROM categories WHERE name = ? LIMIT 1");
    if (!$stmt) {
        jsonError("Prepare failed (categories select): " . $conn->error);
    }
    $stmt->bind_param("s", $category_name);
    $stmt->execute();
    $stmt->store_result();
    if ($stmt->num_rows > 0) {
        $stmt->bind_result($category_id);
        $stmt->fetch();
    }
    $stmt->close();

    if ($category_id === null) {
        $stmt = $conn->prepare("INSERT INTO categories (name, description) VALUES (?, NULL)");
        if (!$stmt) {
            jsonError("Prepare failed (categories insert): " . $conn->error);
        }
        $stmt->bind_param("s", $category_name);
        if (!$stmt->execute()) {
            $err = $stmt->error ?: $conn->error;
            $stmt->close();
            jsonError("Database write failed (categories): " . $err);
        }
        $category_id = $conn->insert_id;
        $stmt->close();
    }

    // 3) coordinates: insert into coordonnees then reference complaints.id_gps
    $coord_id = null;
    $stmt = $conn->prepare("INSERT INTO coordonnees (latitude, longitude) VALUES (?, ?)");
    if (!$stmt) {
        jsonError("Prepare failed (coordonnees insert): " . $conn->error);
    }
    $stmt->bind_param("dd", $latitude, $longitude);
    if (!$stmt->execute()) {
        $err = $stmt->error ?: $conn->error;
        $stmt->close();
        jsonError("Database write failed (coordonnees): " . $err);
    }
    $coord_id = $conn->insert_id;
    $stmt->close();

    // 4) insert complaint
    $stmt = $conn->prepare("
        INSERT INTO complaints (subject, description, status, priority, user_id, category_id, id_gps)
        VALUES (?, ?, 'OUVERT', 'MOYENNE', ?, ?, ?)
    ");
    if (!$stmt) {
        jsonError("Prepare failed (complaints insert): " . $conn->error);
    }
    $stmt->bind_param("ssiii", $type, $message, $user_id, $category_id, $coord_id);
    if (!$stmt->execute()) {
        $err = $stmt->error ?: $conn->error;
        $stmt->close();
        jsonError("Database write failed (complaints): " . $err);
    }
    $complaint_id = $conn->insert_id;
    $stmt->close();

    jsonSuccess("Réclamation envoyée avec succès", ["id" => $complaint_id]);
}

// -----------------------
// GET: list complaints
// (for admin dashboard, if you use it later)
// -----------------------
if ($method === 'GET') {
    $status = isset($_GET['status']) ? trim($_GET['status']) : '';
    $allowed_status = ["OUVERT", "EN_COURS", "RESOLU", "REJETE"];

    $sql = "
        SELECT
            c.id,
            c.subject,
            c.description,
            c.status,
            c.priority,
            c.created_at,
            c.updated_at,
            u.prenom,
            u.nom,
            cat.name AS category_name
        FROM complaints c
        LEFT JOIN users u ON c.user_id = u.id
        LEFT JOIN categories cat ON c.category_id = cat.id
    ";

    if ($status !== '' && $status !== 'tous') {
        if (!in_array($status, $allowed_status, true)) {
            jsonError("Statut invalide");
        }
        $sql .= " WHERE c.status = '" . $conn->real_escape_string($status) . "'";
    }

    $sql .= " ORDER BY c.created_at DESC";

    $result = $conn->query($sql);
    if (!$result) {
        jsonError("Query failed: " . $conn->error);
    }

    $rows = [];
    while ($row = $result->fetch_assoc()) {
        $rows[] = $row;
    }

    echo json_encode(["success" => true, "data" => $rows]);
    exit;
}

jsonError("Méthode non supportée");
