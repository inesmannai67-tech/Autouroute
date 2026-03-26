<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "autouroute";  // Must match: CREATE DATABASE autouroute; (see database_schema.sql)

mysqli_report(MYSQLI_REPORT_ERROR | MYSQLI_REPORT_STRICT);

try {
    $conn = new mysqli($servername, $username, $password, $dbname);
    $conn->set_charset("utf8mb4");
    // Log successful connection
    error_log("Database connection successful");
} catch (Exception $e) {
    $conn = null;
    // Log error for server-side debugging
    $error = "DB Connection Error: " . $e->getMessage();
    error_log($error);
    // Also write to a debug file for easier inspection
    file_put_contents(__DIR__ . '/db_error.log', date('Y-m-d H:i:s') . " - " . $error . PHP_EOL, FILE_APPEND);
}
?>