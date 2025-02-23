package app.daos;

import app.dtos.ActivityDTO;
import app.entities.Activity;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class ActivityDAO implements IDAO<Activity, Long>

{

    private static EntityManagerFactory emf;
    private static ActivityDAO instance;

    private ActivityDAO()
    {
    }

    //Singleton pattern
    public static ActivityDAO getInstance(EntityManagerFactory _emf)
    {
        if (emf == null)
        {
            emf = _emf;
            instance = new ActivityDAO();
        }
        return instance;
    }


    @Override
    public Activity create(Activity activity)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(activity);
            em.getTransaction().commit();
            return activity;
        } catch (Exception e)
        {
            throw new ApiException(401, "Error creating activity", e);
        }
    }

    @Override
    public Activity read(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(Activity.class, id);
        }
    }

    @Override
    public List<Activity> readAll()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT a FROM Activity a", Activity.class).getResultList();
        } catch (Exception e)
        {
            throw new ApiException(401, "Error finding list of activities", e);
        }
    }

    @Override
    public Activity update(Activity type)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            //Merge har en returtype, hvorfor man skal koble det på et updated objekt
            Activity updatedActivity = em.merge(type);
            //Ryd cache
            em.flush();
            //Vi vil gerne sikre os det KUN er det ene objekt der er opdateret der kommer med
            em.refresh(updatedActivity);
            em.getTransaction().commit();
            return updatedActivity;
        } catch (Exception e)
        {
            throw new ApiException(401, "Error updating activity", e);
        }
    }

    @Override
    public void delete(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Activity activity = em.find(Activity.class, id);
            if (activity == null)
            {
                em.getTransaction().rollback();
                throw new ApiException(401, "Error deleting activity, activity was not found");
            }
            em.remove(activity);
            em.getTransaction().commit();
        } catch (Exception e)
        {
            throw new ApiException(401, "Error removing activity", e);
        }

    }

}
