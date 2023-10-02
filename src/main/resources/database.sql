-- Create database
DROP DATABASE IF EXISTS ap_simulaattori;
CREATE DATABASE ap_simulaattori;
USE ap_simulaattori;

-- Statements for creating the airport table
CREATE TABLE airport (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name_description VARCHAR(255) NOT NULL,
                         amount_type DECIMAL(10, 2) NOT NULL
);

-- Statements for populating the airport table with data (example data from our output)
INSERT INTO airport (name_description, amount_type) VALUES
                                                        ('Käynnissä oloaika', 1000.0),
                                                        ('Kikki asiakaat', 280.0),
                                                        ('Lennolle ehtineet', 34.0),
                                                        ('Lennolle myöhästyneet', 246.0),
                                                        ('Kotimaa (T1)', 139.0),
                                                        ('Ulkomaa (T2)', 107.0);

-- Statements for creating the check-in table
CREATE TABLE check_in (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name_description VARCHAR(255) NOT NULL,
                          amount_type DECIMAL(10, 2) NOT NULL
);

-- Statements for populating the check-in table with data (example data from our output)
INSERT INTO check_in (name_description, amount_type) VALUES
                                                         ('Käyttöaste', 96.0),
                                                         ('Suoritusteho', 10.02),
                                                         ('Jonotusaika (KA)', 25.20),
                                                         ('Jonon pituus (KA)', 3.89);

-- Statements for creating the security_check table
CREATE TABLE security_check (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                name_description VARCHAR(255) NOT NULL,
                                amount_type DECIMAL(10, 2) NOT NULL
);

-- Statements for populating the security_check table with data (example data from our output)
INSERT INTO security_check (name_description, amount_type) VALUES
                                                               ('Käyttöaste', 96.0),
                                                               ('Suoritusteho', 10.02),
                                                               ('Jonotusaika (KA)', 25.20),
                                                               ('Jonon pituus (KA)', 3.89);

-- Statements for creating the passport check table
CREATE TABLE passport_check (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                name_description VARCHAR(255) NOT NULL,
                                amount_type DECIMAL(10, 2) NOT NULL
);

-- Statements for populating the passport_check table with data (example data from our output)
INSERT INTO passport_check (name_description, amount_type) VALUES
                                                               ('Käyttöaste', 96.0),
                                                               ('Suoritusteho', 10.02),
                                                               ('Jonotusaika (KA)', 25.20),
                                                               ('Jonon pituus (KA)', 3.89);

-- Statements for creating the terminal 1 (T1) table
CREATE TABLE terminal_1 (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            name_description VARCHAR(255) NOT NULL,
                            amount_type DECIMAL(10, 2) NOT NULL
);

-- Statements for populating the terminal 1 (T1) table with data (example data from our output)
INSERT INTO terminal_1 (name_description, amount_type) VALUES
                                                           ('Käyttöaste', 96.0),
                                                           ('Suoritusteho', 10.02),
                                                           ('Jonotusaika (KA)', 25.20),
                                                           ('Jonon pituus (KA)', 3.89);

-- Statements for creating the terminal 2 (T2) table
CREATE TABLE terminal_2 (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            name_description VARCHAR(255) NOT NULL,
                            amount_type DECIMAL(10, 2) NOT NULL
);

-- Statements for populating the terminal 2 (T2) table with data (example data from our output)
INSERT INTO terminal_2 (name_description, amount_type) VALUES
                                                           ('Käyttöaste', 96.0),
                                                           ('Suoritusteho', 10.02),
                                                           ('Jonotusaika (KA)', 25.20),
                                                           ('Jonon pituus (KA)', 3.89);


-- Create user for the application database and grant privileges
DROP USER IF EXISTS 'appuser'@'localhost';
CREATE USER 'appuser'@'localhost' IDENTIFIED BY 'appuser123';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, DROP ON ap_simulaattori.* TO 'appuser'@'localhost';
