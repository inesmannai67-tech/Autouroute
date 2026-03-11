-- Database for Tunisie Autoroute App
-- Created automatically by auth.php during the first run

CREATE DATABASE IF NOT EXISTS autouroute_db;
USE autouroute_db;

-- Table to store user accounts
CREATE TABLE IF NOT EXISTS users (
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    prenom VARCHAR(50) NOT NULL,
    nom VARCHAR(50) NOT NULL,
    genre VARCHAR(20),
    age INT,
    phone VARCHAR(20) UNIQUE NOT NULL,
    otp VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Example Data
-- INSERT INTO users (prenom, nom, genre, age, phone) VALUES ('Flen', 'Ben Foulen', 'Homme', 30, '21612345678');
