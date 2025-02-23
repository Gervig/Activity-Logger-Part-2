package app;

import app.config.HibernateConfig;
import app.dtos.UserDTO;
import app.populators.UserDTOPopulator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Main
{
    public static void main(String[] args)
    {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        UserDTO[] userDTOS = UserDTOPopulator.populate();

        // Close the database connection:
        em.close();
        emf.close();
    }

}
