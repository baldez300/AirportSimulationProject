DROP DATABASE IF EXISTS ap_simulaattori;
CREATE DATABASE ap_simulaattori;
USE ap_simulaattori;
DROP USER IF EXISTS 'appuser'@'localhost';
CREATE USER 'appuser'@'localhost' IDENTIFIED BY 'appuser123';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, DROP ON ap_simulaattori.* TO 'appuser'@'localhost';
DROP TABLE IF EXISTS tulokset;
CREATE TABLE tulokset (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    paivamaara CHARVAR(20),
    aika DOUBLE,
    asiakkaat DOUBLE,
    lennolle_ehtineet DOUBLE,
    lennolta_myohastyneet DOUBLE,
    myohastyneet_t1 DOUBLE,
    myohastyneet_t2 DOUBLE
);
DROP TABLE IF EXISTS lahtoselvitys;
CREATE TABLE lahtoselvitys (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    kayttoaste DOUBLE,
    suoritusteho DOUBLE,
    jonotusaika DOUBLE,
    jononpituus DOUBLE
);
DROP TABLE IF EXISTS turvatarkastus;
CREATE TABLE turvatarkastus (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    kayttoaste DOUBLE,
    suoritusteho DOUBLE,
    jonotusaika DOUBLE,
    jononpituus DOUBLE
);
DROP TABLE IF EXISTS passintarkastus;
CREATE TABLE passintarkastus (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    kayttoaste DOUBLE,
    suoritusteho DOUBLE,
    jonotusaika DOUBLE,
    jononpituus DOUBLE
);
DROP TABLE IF EXISTS t1;
CREATE TABLE t1 (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    kayttoaste DOUBLE,
    suoritusteho DOUBLE,
    jonotusaika DOUBLE,
    jononpituus DOUBLE
);
DROP TABLE IF EXISTS t2;
CREATE TABLE t2 (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    kayttoaste DOUBLE,
    suoritusteho DOUBLE,
    jonotusaika DOUBLE,
    jononpituus DOUBLE
);
