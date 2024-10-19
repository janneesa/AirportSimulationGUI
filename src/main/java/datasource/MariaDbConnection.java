package datasource;

import jakarta.persistence.*;

/**
 * The class for the database connection.
 */

public class MariaDbConnection {

    private static EntityManagerFactory emf = null;
    private static EntityManager em = null;

    public static EntityManager getInstance() {
        if (em == null) {
            if (emf == null) {
                emf = Persistence.createEntityManagerFactory("airportDatabase");
            }
            em = emf.createEntityManager();
        }
        return em;




    }
}
