package app.daos;

import app.dtos.CurrentDataDTO;
import app.entities.CurrentData;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class CurrentDataDAO implements IDAO<CurrentData, Long>
{

    private static EntityManagerFactory emf;
    private static CurrentDataDAO instance;

    private CurrentDataDAO()
    {
    }

    //Singleton pattern
    public static CurrentDataDAO getInstance(EntityManagerFactory _emf)
    {
        if (emf == null)
        {
            emf = _emf;
            instance = new CurrentDataDAO();
        }
        return instance;
    }


    @Override
    public CurrentData create(CurrentData type)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(type);
            em.getTransaction().commit();
            return type;
        } catch (Exception e)
        {
            throw new ApiException(401, "Error creating currentData", e);
            //Eventuelt kunne man have en rollback
        }
    }

    @Override
    public CurrentData read(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(CurrentData.class, id);
        }
    }

    @Override
    public List<CurrentData> readAll()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT c FROM CurrentData c", CurrentData.class).getResultList();
        } catch (Exception e)
        {
            throw new ApiException(401, "Error finding list of currentData", e);
        }
    }

    @Override
    public CurrentData update(CurrentData type)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            //Merge har en returtype, hvorfor man skal koble det på et updated objekt
            CurrentData updatedCurrentData = em.merge(type);
            //Ryd cache
            em.flush();
            //Vi vil gerne sikre os det KUN er det ene objekt der er opdateret der kommer med
            em.refresh(updatedCurrentData);
            em.getTransaction().commit();
            return updatedCurrentData;
        } catch (Exception e)
        {
            throw new ApiException(401, "Error updating currentData", e);
        }
    }

    @Override
    public void delete(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            CurrentData currentData = em.find(CurrentData.class, id);
            if (currentData == null)
            {
                em.getTransaction().rollback();
                throw new ApiException(401, "Error deleting currentData, currentData was not found");
            }
            em.remove(currentData);
            em.getTransaction().commit();
        } catch (Exception e)
        {
            throw new ApiException(401, "Error removing currentData", e);
        }
    }
}
