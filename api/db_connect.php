<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "autouroute";  // Must match: CREATE DATABASE autouroute; (see database_schema.sql)

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    // Don't die() - let auth.php return proper JSON so the app can show the error
    $conn = null;
}
?>