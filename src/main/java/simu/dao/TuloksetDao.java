package simu.dao;

import simu.framework.IDao;
import simu.entity.*;
import simu.datasource.SQLconnection;

import java.util.List;

import jakarta.persistence.EntityManager;

public class TuloksetDao implements IDao {

    @Override
    // Tallentaa tulokset tietokantaan HashMapista
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
    public List<Tulokset> lataaKaikki() {
        EntityManager em = SQLconnection.getInstance();
        List<Tulokset> tulokset = em.createQuery("SELECT t FROM Tulokset t", Tulokset.class).getResultList();
        return tulokset;
    }
}
