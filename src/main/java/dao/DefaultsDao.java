package dao;

import jakarta.persistence.EntityManager;
import datasource.MariaDbConnection;
import simu.model.Defaults;
import java.util.ArrayList;

public class DefaultsDao {
    public void persist(Defaults def) {
        EntityManager em = MariaDbConnection.getInstance();
        em.getTransaction().begin();
        em.persist(def);
        em.getTransaction().commit();
    }

    public ArrayList<Defaults> getDefaults() {
        EntityManager em = MariaDbConnection.getInstance();
        return new ArrayList<>(em.createQuery("SELECT c FROM Defaults c", Defaults.class).getResultList());
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
