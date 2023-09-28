package simu.dao;

import simu.framework.IDao;
import simu.entity.*;
import simu.datasource.SQLconnection;

import java.util.List;
import java.util.HashMap;

import jakarta.persistence.EntityManager;

public class TuloksetDao implements IDao {

    @Override
    // Tallentaa tulokset tietokantaan
    public void tallenna(Tulokset tulos, LSTulos pptulos, TTTulos tttulos, PTTulos pttulos, T1Tulos t1tulos, T2Tulos t2tulos) {
        try {
            EntityManager em = SQLconnection.getInstance();
            em.getTransaction().begin();
            em.persist(tulos);
            em.persist(pptulos);
            em.persist(tttulos);
            em.persist(pttulos);
            em.persist(t1tulos);
            em.persist(t2tulos);
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

    @Override
    /* Lataa tarkemmat tiedot tietokannasta. Koska HashMappiin tulee monta eri tyyppiä, joudutaan
    *  käyttämään Object-tyyppiä. */
    public HashMap<Object, Object> lataaTarkemmatTiedot(int id) {
        EntityManager em = SQLconnection.getInstance();
    
        Tulokset tulos = em.find(Tulokset.class, id);
        LSTulos lsTulos = em.find(LSTulos.class, id);
        TTTulos ttTulos = em.find(TTTulos.class, id);
        PTTulos ptTulos = em.find(PTTulos.class, id);
        T1Tulos t1Tulos = em.find(T1Tulos.class, id);
        T2Tulos t2Tulos = em.find(T2Tulos.class, id);
        HashMap<Object, Object> tarkemmatTiedot = new HashMap<>();
        tarkemmatTiedot.put("SL", (Tulokset) tulos);
        tarkemmatTiedot.put("LS", (LSTulos) lsTulos);
        tarkemmatTiedot.put("TT", (TTTulos) ttTulos);
        tarkemmatTiedot.put("PT", (PTTulos) ptTulos);
        tarkemmatTiedot.put("T1", (T1Tulos) t1Tulos);
        tarkemmatTiedot.put("T2", (T2Tulos) t2Tulos);
        return tarkemmatTiedot;
    }
}
