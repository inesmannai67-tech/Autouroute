<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "autouroute";  // Must match: CREATE DATABASE autouroute; (see database_schema.sql)

mysqli_report(MYSQLI_REPORT_ERROR | MYSQLI_REPORT_STRICT);

try {
    $conn = new mysqli($servername, $username, $password, $dbname);
    $conn->set_charset("utf8mb4");
} catch (Exception $e) {
    $conn = null;
    // Log error for server-side debugging
    error_log("DB Connection Error: " . $e->getMessage());
}
?>