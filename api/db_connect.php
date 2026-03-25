<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "autouroute";  // Must match: CREATE DATABASE autouroute; (see database_schema.sql)

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    // If connection fails, keep $conn but line 19 in auth.php will check $conn->connect_error
    // This is safer than setting it to null immediately
}
?>