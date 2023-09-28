package simu.dao;

import simu.framework.IDao;
import simu.entity.*;
import simu.datasource.SQLconnection;

import java.util.List;

import jakarta.persistence.EntityManager;

public class TuloksetDao implements IDao {
    @Override
    public void tallenna(Tulokset tulos, LSTulos pptulos, TTTulos tttulos, PTTulos pttulos, T1Tulos t1tulos, T2Tulos t2tulos) {
        try {
            EntityManager em = SQLconnection.getInstance();
            em.getTransaction().begin();
            em.persist(tulos);
            em.persist(pptulos);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void poista(Tulokset tulos) {

    }
    @Override
    public List<Tulokset> lataaKaikki() {
        return null;
    }
    @Override
    public Tulokset lataaYksi() {
        return null;
    }
}
