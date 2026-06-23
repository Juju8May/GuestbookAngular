-- Guestbook MariaDB Database Initialization

-- Create Database with UTF-8 character set and collation
CREATE DATABASE IF NOT EXISTS guestbook_Maria_Db 
  CHARACTER SET utf8mb4 
  COLLATE utf8mb4_unicode_ci;

-- Use the database
USE guestbook_Maria_Db;

-- Create application user with password
CREATE USER IF NOT EXISTS 'guestbook_user'@'localhost' IDENTIFIED BY 'guestbook_password';

-- Grant privileges to the application user
GRANT ALL PRIVILEGES ON guestbook_Maria_Db.* TO 'guestbook_user'@'localhost';

-- Apply privilege changes
FLUSH PRIVILEGES;

-- Set database properties
SET SESSION default_storage_engine = 'InnoDB';
SET SESSION sql_mode = 'STRICT_TRANS_TABLES,NO_ZERO_DATE,NO_ZERO_IN_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
