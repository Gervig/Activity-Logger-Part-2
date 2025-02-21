package app.daos;

import app.dtos.WeatherInfoDTO;
import app.entities.WeatherInfo;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class WeatherInfoDAO implements IDAO<WeatherInfo, Long>
{

    private static EntityManagerFactory emf;
    private static WeatherInfoDAO INSTANCE;

    //Constructoren - fordi det er singleton pattern, laver man en privat constructor så den er cuttet af og andre ikke kan bruge den
    private WeatherInfoDAO(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    //Singleton pattern
    public static WeatherInfoDAO getInstance(EntityManagerFactory emf)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new WeatherInfoDAO(emf);
        }
        return INSTANCE;
    }
    @Override
    public WeatherInfo create(WeatherInfo type)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(type);
            em.getTransaction().commit();
            return type;
        } catch (Exception e)
        {
            throw new ApiException(401, "Error creating weatherInfo", e);
            //Eventuelt kunne man have en rollback
        }
    }

    @Override
    public WeatherInfo read(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(WeatherInfo.class, id);
        }
    }

    @Override
    public List<WeatherInfo> readAll()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT w FROM WeatherInfo w", WeatherInfo.class).getResultList();
        } catch (Exception e)
        {
            throw new ApiException(401, "Error finding list of weather info", e);
        }
    }

    @Override
    public WeatherInfo update(WeatherInfo type)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            //Merge har en returtype, hvorfor man skal koble det på et updated objekt
            WeatherInfo updatedWeatherInfo = em.merge(type);
            //Ryd cache
            em.flush();
            //Vi vil gerne sikre os det KUN er det ene objekt der er opdateret der kommer med
            em.refresh(updatedWeatherInfo);
            em.getTransaction().commit();
            return updatedWeatherInfo;
        } catch (Exception e)
        {
            throw new ApiException(401, "Error updating weather information", e);
        }
    }

    @Override
    public void delete(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            WeatherInfo weatherInfo = em.find(WeatherInfo.class, id);
            if (weatherInfo == null)
            {
                em.getTransaction().rollback();
                throw new ApiException(401, "Error deleting weatherInfo, weatherInfo was not found");
            }
            em.remove(weatherInfo);
            em.getTransaction().commit();
        } catch (Exception e)
        {
            throw new ApiException(401, "Error removing weatherInfo", e);
        }

    }

    //Task 3
    //Could this be void?
    public WeatherInfo persistWeatherInfo(WeatherInfoDTO weatherInfoDTO){
        WeatherInfo weatherInfo = WeatherInfo.builder()
                .locationName(weatherInfoDTO.getLocationName())
                .build();
        create(weatherInfo);
        return weatherInfo;
    }

}
