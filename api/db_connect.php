<?php
$host = "127.0.0.1";
$user = "root";
$pass = "";
$dbname = "autouroute_db";

$conn = new mysqli($host, $user, $pass);

if ($conn->connect_error) {
    die(json_encode(["success" => false, "message" => "Connection failed: " . $conn->connect_error]));
}

// Create database if not exists
$conn->query("CREATE DATABASE IF NOT EXISTS $dbname");
$conn->select_db($dbname);

// Create users table if not exists
$tableSql = "CREATE TABLE IF NOT EXISTS users (
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    prenom VARCHAR(50) NOT NULL,
    nom VARCHAR(50) NOT NULL,
    genre VARCHAR(20),
    age INT,
    phone VARCHAR(20) UNIQUE NOT NULL,
    otp VARCHAR(10)
)";
$conn->query($tableSql);
?>
