package app.services;

import app.daos.ActivityDAO;
import app.daos.CityInfoDAO;
import app.daos.WeatherInfoDAO;
import app.dtos.*;
import app.entities.*;
import app.entities.Activity;
import app.entities.Users;
import jakarta.persistence.EntityManagerFactory;

import java.util.HashSet;
import java.util.Set;

public class ActivityService
{
    //Create an ActivityService that can call the DAO class and persist the data in the database.
    private final ActivityDAO activityDAO;

    public ActivityService(ActivityDAO activityDAO)
    {
        this.activityDAO = activityDAO;
    }

    //Could this be void?
    public Activity persistActivity(Users user, ActivityDTO activityDTO, CityInfoDTO cityInfoDTO, WeatherInfoDTO weatherInfoDTO, CurrentDataDTO currentDataDTO)
    {
        CityService cityService = new CityService();
        WeatherService weatherService = new WeatherService();
        WeatherInfo weatherInfo = weatherService.buildWeatherInfo(weatherInfoDTO, currentDataDTO);
        CityInfo cityInfo = cityService.buildCityInfo(cityInfoDTO);

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

        Set<Activity> activitySet = new HashSet<>();

        user.setActivities(activitySet);
        user.addActivity(activity);

        return activity;
    }
}
