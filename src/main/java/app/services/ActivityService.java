package app.services;

import app.callables.CityServiceCallable;
import app.daos.ActivityDAO;
import app.daos.CityInfoDAO;
import app.daos.WeatherInfoDAO;
import app.dtos.*;
import app.entities.*;
import jakarta.persistence.EntityManagerFactory;

public class ActivityService
{
    //Create an ActivityService that can call the DAO class and persist the data in the database.
    private static EntityManagerFactory emf;
    private final ActivityDAO activityDAO;
    private final CityInfoDAO cityInfoDAO;
    private final WeatherInfoDAO weatherInfoDAO;

    public ActivityService(ActivityDAO activityDAO, CityInfoDAO cityInfoDAO, WeatherInfoDAO weatherInfoDAO)
    {
     this.activityDAO = activityDAO;
     this.weatherInfoDAO = weatherInfoDAO;
     this.cityInfoDAO = cityInfoDAO;
    }

    //Could this be void?
    public Activity persistActivity(Users user, ActivityDTO activityDTO, CityInfoDTO cityInfoDTO, WeatherInfoDTO weatherInfoDTO, CurrentDataDTO currentDataDTO)
    {
        CityService cityService = new CityService();
        WeatherService weatherService = new WeatherService();
        WeatherInfo weatherInfo = weatherService.persistWeatherInfo(weatherInfoDTO, currentDataDTO);
        CityInfo cityInfo = cityService.persistCityInfo(cityInfoDTO);

        Activity activity = Activity.builder()
                .exerciseDate(activityDTO.getExerciseDate())
                .exerciseType(activityDTO.getExerciseType())
                .timeOfDay(activityDTO.getTimeOfDay())
                .duration(activityDTO.getDuration())
                .distance(activityDTO.getDistance())
                .comment(activityDTO.getComment())
                .users(user)
                .weatherInfo(weatherInfo)
                .cityInfo(cityInfo)
                .build();

        activity = activityDAO.create(activity);

        return activity;
    }
}
