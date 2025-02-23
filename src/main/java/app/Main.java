package app;

import app.callables.CityServiceCallable;
import app.callables.WeatherServiceCallable;
import app.config.HibernateConfig;
import app.daos.impl.ActivityDAO;
import app.daos.impl.UserDAO;
import app.dtos.*;
import app.entities.Users;
import app.populators.ActivityDTOPopulator;
import app.populators.UserDTOPopulator;
import app.services.ActivityService;
import app.services.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        // Populates generic user and activity data
        UserDTO[] userDTOS = UserDTOPopulator.populate();
        ActivityDTO[] activityDTOS = ActivityDTOPopulator.populate();

        // Scalable can call multiple cities
        String[] cityNames = new String[]{
                "Roskilde"
        };

        // Creates DTOs from API data
        List<CityInfoDTO> cityInfoDTOS = CityServiceCallable.getCityInfoDTOs(cityNames);
        List<WeatherInfoDTO> weatherInfoDTOS = WeatherServiceCallable.getWeatherInfoDTOs(cityNames);

        // Instantiates DAO instances for persisting Entities in DB
        ActivityDAO activityDAO = ActivityDAO.getInstance(emf);
        UserDAO userDAO = UserDAO.getInstance(emf);

        // Instantiates User service, for handling DTO
        UserService userService = new UserService(userDAO);

        // Builds a Users entity from DTO and persists in DB
        Users u1 = userService.persistUser(userDTOS[0]);

        // Sets DTOs from populate array and lists
        ActivityDTO a1 = activityDTOS[0];
        CityInfoDTO ci1 = cityInfoDTOS.get(0);
        WeatherInfoDTO wi1 = weatherInfoDTOS.get(0);

        // Instantiates service, for handling DTOs
        ActivityService activityService = new ActivityService(activityDAO);

        // Builds an Activity entity from DTOs and persists in DB
        activityService.persistActivity(u1, a1, ci1, wi1);

        // Close the database connection:
        em.close();
        emf.close();
    }

}
