package app.daos;

import app.entities.ActivityDTO;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class ActivityDAO implements IDAO<ActivityDTO, Long>

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
    public ActivityDTO create(ActivityDTO activity)
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
    public ActivityDTO read(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(ActivityDTO.class, id);
        }
    }

    @Override
    public List<ActivityDTO> readAll()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT a FROM ActivityDTO a", ActivityDTO.class).getResultList();
        } catch (Exception e)
        {
            throw new ApiException(401, "Error finding list of activities", e);
        }
    }

    @Override
    public ActivityDTO update(ActivityDTO type)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            //Merge har en returtype, hvorfor man skal koble det på et updated objekt
            ActivityDTO updatedActivity = em.merge(type);
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
            ActivityDTO activity = em.find(ActivityDTO.class, id);
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
