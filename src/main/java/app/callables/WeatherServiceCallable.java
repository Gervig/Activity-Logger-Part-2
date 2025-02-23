package app.callables;

import app.dtos.CityInfoDTO;
import app.dtos.WeatherInfoDTO;
import app.services.CityService;
import app.services.WeatherService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class WeatherServiceCallable implements Callable<WeatherInfoDTO>
{
    String cityName;

    public WeatherServiceCallable(String cityName)
    {
        this.cityName = cityName;
    }

    @Override
    public WeatherInfoDTO call() throws Exception
    {
        WeatherInfoDTO weatherInfoDTO = WeatherService.fetchWeatherDataByLocationName(cityName);
        return weatherInfoDTO;
    }

    public static List<WeatherInfoDTO> getWeatherInfoDTOs(String[] cityNames)
    {
        List<Future<WeatherInfoDTO>> futureList = new ArrayList<>();
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (String cityName : cityNames)
        {
            Callable<WeatherInfoDTO> task = new WeatherServiceCallable(cityName);
            Future<WeatherInfoDTO> future = executorService.submit(task);
            futureList.add(future);
        }

        List<WeatherInfoDTO> weatherInfoDTOS = new ArrayList<>();

        for (Future<WeatherInfoDTO> weatherInfoDTOFuture : futureList)
        {
            try
            {
                WeatherInfoDTO finishedTask = weatherInfoDTOFuture.get();
                weatherInfoDTOS.add(finishedTask);
            } catch (InterruptedException | ExecutionException e)
            {
                System.err.println("Error retrieving data for a city: " + e.getMessage());
                e.printStackTrace();
            }
        }

        executorService.shutdown();
        return weatherInfoDTOS;
    }
}
