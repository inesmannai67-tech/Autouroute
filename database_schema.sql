-- Autouroute Database Setup
-- Run in phpMyAdmin: http://localhost/phpmyadmin
-- Or: mysql -u root < database_schema.sql

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS otp_codes;
DROP TABLE IF EXISTS sessions;
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    prenom VARCHAR(50) NOT NULL DEFAULT '',
    nom VARCHAR(50) NOT NULL DEFAULT '',
    genre VARCHAR(20) DEFAULT '',
    age INT DEFAULT 0,
    num_telephone VARCHAR(20) UNIQUE NOT NULL,
    otp VARCHAR(10) DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
