package dao;

import jakarta.persistence.EntityManager;
import datasource.MariaDbConnection;
import simu.model.Results;
import java.util.ArrayList;

public class ResultsDao {
    public void persist(Results res) {
        EntityManager em = MariaDbConnection.getInstance();
        em.getTransaction().begin();
        em.persist(res);
        em.getTransaction().commit();
    }

    public ArrayList<Results> getResults() {
        EntityManager em = MariaDbConnection.getInstance();
        return new ArrayList<>(em.createQuery("SELECT c FROM Results c", Results.class).getResultList());
    }

    public void update(Results res) {
        EntityManager em = MariaDbConnection.getInstance();
        em.getTransaction().begin();
        em.merge(res);
        em.getTransaction().commit();
    }

    public void delete(Results res) {
        EntityManager em = MariaDbConnection.getInstance();
        em.getTransaction().begin();
        em.remove(res);
        em.getTransaction().commit();
    }
}
