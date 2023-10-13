package simu.dao;

import simu.framework.IDao;
import simu.entity.*;
import simu.datasource.SQLconnection;

import java.util.List;

import jakarta.persistence.EntityManager;
/**
 * TuloksetDao-luokka, joka toteuttaa IDao-rajapinnan
 * @see simu.framework.IDao
 */
public class TuloksetDao implements IDao {

    @Override
    // Tallentaa tulokset tietokantaan HashMapista
    /**
     * Tallentaa tulokset tietokantaan HashMapista
     * @param tulokset
     */
    public void tallenna(Tulokset tulokset) {
        try {
            EntityManager em = SQLconnection.getInstance();
            em.getTransaction().begin();
            em.persist(tulokset);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    // Lataa kaikki tulokset tietokannasta
    /**
     * Lataa kaikki tulokset tietokannasta
     * @return List<Tulokset>
     */
    public List<Tulokset> lataaKaikki() {
        EntityManager em = SQLconnection.getInstance();
        List<Tulokset> tulokset = em.createQuery("SELECT t FROM Tulokset t", Tulokset.class).getResultList();
        return tulokset;
    }
}
