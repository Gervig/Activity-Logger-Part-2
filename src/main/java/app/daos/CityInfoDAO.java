package app.daos;

import app.dtos.ActivityDTO;
import app.dtos.CityInfoDTO;
import app.entities.Activity;
import app.entities.CityInfo;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class CityInfoDAO implements IDAO<CityInfo, Long>
{

    private static EntityManagerFactory emf;
    private static CityInfoDAO INSTANCE;

    //Constructoren - fordi det er singleton pattern, laver man en privat constructor så den er cuttet af og andre ikke kan bruge den
    private CityInfoDAO(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    //Singleton pattern
    public static CityInfoDAO getInstance(EntityManagerFactory emf)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new CityInfoDAO(emf);
        }
        return INSTANCE;
    }

    @Override
    public CityInfo create(CityInfo type)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(type);
            em.getTransaction().commit();
            return type;
        } catch (Exception e)
        {
            throw new ApiException(401, "Error creating cityInfo", e);
            //Eventuelt kunne man have en rollback
        }
    }

    @Override
    public CityInfo read(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(CityInfo.class, id);
        }
    }

    @Override
    public List<CityInfo> readAll()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT c FROM CityInfo c", CityInfo.class).getResultList();
        } catch (Exception e)
        {
            throw new ApiException(401, "Error finding list of city info", e);
        }
    }

    @Override
    public CityInfo update(CityInfo type)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            //Merge har en returtype, hvorfor man skal koble det på et updated objekt
            CityInfo updatedCityInfo = em.merge(type);
            //Ryd cache
            em.flush();
            //Vi vil gerne sikre os det KUN er det ene objekt der er opdateret der kommer med
            em.refresh(updatedCityInfo);
            em.getTransaction().commit();
            return updatedCityInfo;
        } catch (Exception e)
        {
            throw new ApiException(401, "Error updating city information", e);
        }
    }

    @Override
    public void delete(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            CityInfo cityInfo = em.find(CityInfo.class, id);
            if (cityInfo == null)
            {
                em.getTransaction().rollback();
                throw new ApiException(401, "Error deleting cityInfo, cityInfo was not found");
            }
            em.remove(cityInfo);
            em.getTransaction().commit();
        } catch (Exception e)
        {
            throw new ApiException(401, "Error removing cityInfo", e);
        }
    }

    //Task 3
    //Could this be void?
    public CityInfo persistCityInfo(CityInfoDTO cityInfoDTO){
        CityInfo cityInfo = CityInfo.builder()
                .name(cityInfoDTO.getName())
                .url(cityInfoDTO.getUrl())
                .latitude(cityInfoDTO.getVisualCenter().get(0))
                .longitude(cityInfoDTO.getVisualCenter().get(1))
                .build();
        create(cityInfo);
        return cityInfo;
    }
}
