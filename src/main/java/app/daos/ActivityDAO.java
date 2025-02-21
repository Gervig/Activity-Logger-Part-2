package app.daos;

import app.dtos.ActivityDTO;
import app.entities.Activity;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class ActivityDAO implements IDAO<Activity,Long>

{

    private static EntityManagerFactory emf;
    private static ActivityDAO INSTANCE;

    //Constructoren - fordi det er singleton pattern, laver man en privat constructor så den er cuttet af og andre ikke kan bruge den
    private ActivityDAO(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    //Singleton pattern
    public static ActivityDAO getInstance(EntityManagerFactory emf)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new ActivityDAO(emf);
        }
        return INSTANCE;
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
            //Eventuelt kunne man have en rollback
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
