package app.daos.impl;

import app.daos.IDAO;
import app.entities.WeatherInfo;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class WeatherInfoDAO implements IDAO<WeatherInfo, Long>
{

    private static EntityManagerFactory emf;
    private static WeatherInfoDAO instance;

    private WeatherInfoDAO()
    {
    }

    //Singleton pattern
    public static WeatherInfoDAO getInstance(EntityManagerFactory _emf)
    {
        if (emf == null)
        {
            emf = _emf;
            instance = new WeatherInfoDAO();
        }
        return instance;
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

}
