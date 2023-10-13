package simu.datasource;

import jakarta.persistence.*;
/**
 * SQLconnection Luokka
 *
 * Luokka luo yhteyden tietokantaan
 *
 * @version 1.0 3 Dec 2020
 * @Author Outi Somi, Jari Korhonen
 * @see simu.model
 */
public class SQLconnection {

    /**
     * emf and em muuttujat
     *
     * Nämä muuttujat luovat yhteyden tietokantaan
     *
     */
    private static EntityManagerFactory emf = null;

    /**
     * em muutuja
     *
     * Tämä muuttuja luo yhteyden tietokantaan
     *
     */
    private static EntityManager em = null;

    /**
     * getInstance metodi
     *
     * Tämä metodi luo yhteyden tietokantaan
     *
     * @return em
     */
    public static EntityManager getInstance() {
        // you need to add synchronization if you run in a multi-threaded environment

        if (em == null) {
            if (emf == null) {
                emf = Persistence.createEntityManagerFactory("CompanyMariaDbUnit");
            }
            em = emf.createEntityManager();
        }
        return em;
    }

    /**
     * close metodi
     *
     * Tämä metodi sulkee yhteyden tietokantaan
     *
     */
    public static void testaaTietokantaYhteys() {
            EntityManagerFactory emf_tester = Persistence.createEntityManagerFactory("CompanyMariaDbUnit");
            emf_tester.close();
    }
}
