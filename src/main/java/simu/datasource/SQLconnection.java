package simu.datasource;

import jakarta.persistence.*;
/**
 * SQLconnection class
 *
 * This class is used to create a connection to the database
 *
 * @version 1.0 3 Dec 2020
 * @Author Outi Somi, Jari Korhonen
 * @see simu.model
 */
public class SQLconnection {

    /**
     * emf and em variables
     *
     * These variables are used to create a connection to the database
     *
     */
    private static EntityManagerFactory emf = null;

    /**
     * em variable
     *
     * This variable is used to create a connection to the database
     *
     */
    private static EntityManager em = null;

    /**
     * getInstance method
     *
     * This method is used to create a connection to the database
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
     * close method
     *
     * This method is used to close the connection to the database
     *
     */
    public static void testaaTietokantaYhteys() {
            EntityManagerFactory emf_tester = Persistence.createEntityManagerFactory("CompanyMariaDbUnit");
            emf_tester.close();
    }
}
