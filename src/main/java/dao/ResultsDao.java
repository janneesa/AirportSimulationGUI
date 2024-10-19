package dao;

import jakarta.persistence.EntityManager;
import datasource.MariaDbConnection;
import simu.model.Results;
import java.util.ArrayList;

/**
 * The data access object for the results.
 *
 * @see simu.model.Results
 */

public class ResultsDao {

    /**
     * Persist the results to the database.
     *
     * @param res the results to persist
     */
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
