package app.services;

import app.daos.impl.ActivityDAO;
import app.dtos.*;
import app.entities.*;
import app.entities.Activity;
import app.entities.Users;

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
    public Activity persistActivity(Users user, ActivityDTO activityDTO, CityInfoDTO cityInfoDTO, WeatherInfoDTO weatherInfoDTO)
    {
        CityService cityService = new CityService();
        WeatherService weatherService = new WeatherService();
        WeatherInfo weatherInfo = weatherService.buildWeatherInfo(weatherInfoDTO);
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

        //TODO: fix this
        // For some reason I have to make a new Set,
        // the Users entity doesn't have one when initialized
        // therefor it's null ??
        Set<Activity> activitySet = new HashSet<>();

        //TODO: also fix this
        // Sets the set of activities for the Users entity,
        // again why I have to do this. I don't know, I hate it
        user.setActivities(activitySet);

        // adds the Activity entity to the Users entity
        user.addActivity(activity);

        return activity;
    }
}
