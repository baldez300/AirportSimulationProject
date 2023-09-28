DROP DATABASE IF EXISTS ap_simulaattori;
CREATE DATABASE ap_simulaattori;
USE ap_simulaattori;
DROP USER IF EXISTS 'appuser'@'localhost';
CREATE USER 'appuser'@'localhost' IDENTIFIED BY 'appuser123';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, DROP ON ap_simulaattori.* TO 'appuser'@'localhost';
