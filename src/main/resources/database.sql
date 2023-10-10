DROP DATABASE IF EXISTS ap_simulaattori;
CREATE DATABASE ap_simulaattori;
USE ap_simulaattori;
DROP USER IF EXISTS 'appuser'@'localhost';
CREATE USER 'appuser'@'localhost' IDENTIFIED BY 'appuser123';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, DROP ON ap_simulaattori.* TO 'appuser'@'localhost';

CREATE TABLE lahtoselvitys(
    id INT NOT NULL AUTO_INCREMENT,
    jononpituus DOUBLE,
    jonotusaika DOUBLE,
    kayttoaste DOUBLE,
    suoritusteho DOUBLE,
    maara INTEGER,
    PRIMARY KEY (id)
);
CREATE TABLE passintarkastus(
    id INT NOT NULL AUTO_INCREMENT,
    jononpituus DOUBLE,
    jonotusaika DOUBLE,
    kayttoaste DOUBLE,
    suoritusteho DOUBLE,
    maara INTEGER,
    PRIMARY KEY (id)
);
CREATE TABLE t1(
    id INT NOT NULL AUTO_INCREMENT,
    jononpituus DOUBLE,
    jonotusaika DOUBLE,
    kayttoaste DOUBLE,
    suoritusteho DOUBLE,
    PRIMARY KEY (id)
);
CREATE TABLE t2(
    id INT NOT NULL AUTO_INCREMENT,
    jononpituus DOUBLE,
    jonotusaika DOUBLE,
    kayttoaste DOUBLE,
    suoritusteho DOUBLE,
    PRIMARY KEY (id)
);
CREATE TABLE turvatarkastus(
    id INT NOT NULL AUTO_INCREMENT,
    jononpituus DOUBLE,
    jonotusaika DOUBLE,
    kayttoaste DOUBLE,
    suoritusteho DOUBLE,
    maara INTEGER,
    PRIMARY KEY (id)
);
CREATE TABLE tulokset(
    tulokset_id INT NOT NULL AUTO_INCREMENT,
    aika DOUBLE,
    asiakkaat DOUBLE,
    lennolle_ehtineet DOUBLE,
    myohastyneet DOUBLE,
    myohastyneet_t1 DOUBLE,
    myohastyneet_t2 DOUBLE,
    paivamaara DATE,
    PRIMARY KEY (tulokset_id),
    lsTulos_id INTEGER,
    ptTulos_id INTEGER,
    t1Tulos_id INTEGER,      
    t2Tulos_id INTEGER,
    ttTulos_id INTEGER,
    FOREIGN KEY (lsTulos_id) REFERENCES lahtoselvitys(id),
    FOREIGN KEY (ttTulos_id) REFERENCES turvatarkastus(id),
    FOREIGN KEY (ptTulos_id) REFERENCES passintarkastus(id),
    FOREIGN KEY (t1Tulos_id) REFERENCES t1(id),
    FOREIGN KEY (t2Tulos_id) REFERENCES t2(id)
);
