package app;

import app.callables.CityServiceCallable;
import app.callables.WeatherServiceCallable;
import app.config.HibernateConfig;
import app.dtos.ActivityDTO;
import app.dtos.CityInfoDTO;
import app.dtos.UserDTO;
import app.dtos.WeatherInfoDTO;
import app.populators.ActivityDTOPopulator;
import app.populators.UserDTOPopulator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        UserDTO[] userDTOS = UserDTOPopulator.populate();

        ActivityDTO[] activityDTOS = ActivityDTOPopulator.populate();

        // Scalable can call multiple cities
        String[] cityNames = new String[]{
                "Roskilde"
        };

        List<CityInfoDTO> cityInfoDTOS = CityServiceCallable.getCityInfoDTOs(cityNames);
        List<WeatherInfoDTO> weatherInfoDTOS = WeatherServiceCallable.getWeatherInfoDTOs(cityNames);

        // Close the database connection:
        em.close();
        emf.close();
    }

}
