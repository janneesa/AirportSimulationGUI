package dao;

import jakarta.persistence.EntityManager;
import datasource.MariaDbConnection;
import simu.model.Defaults;
import java.util.ArrayList;

/**
 * The data access object for the simulation parameters.
 *
 * @see simu.model.Defaults
 */

public class DefaultsDao {

    /**
     * Persist the parameters to the database.
     *
     * @param def the data to persist
     */
    public void persist(Defaults def) {
        EntityManager em = MariaDbConnection.getInstance();
        em.getTransaction().begin();
        em.persist(def);
        em.getTransaction().commit();
    }

    /**
     * Get the latest parameters from the database.
     *
     * @return the parameters
     */
    public Defaults getDefaults() {
        EntityManager em = MariaDbConnection.getInstance();
        return em.createQuery("SELECT d FROM Defaults d ORDER BY d.id DESC", Defaults.class).setMaxResults(1).getSingleResult();
    }

    public void update(Defaults def) {
        EntityManager em = MariaDbConnection.getInstance();
        em.getTransaction().begin();
        em.merge(def);
        em.getTransaction().commit();
    }

    public void delete(Defaults def) {
        EntityManager em = MariaDbConnection.getInstance();
        em.getTransaction().begin();
        em.remove(def);
        em.getTransaction().commit();
    }
}
