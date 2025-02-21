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

    public List<WeatherInfoDTO> getWeatherInfoDTOs(String[] cityNames) throws ExecutionException, InterruptedException
    {
        List<Future<WeatherInfoDTO>> futureList = new ArrayList<>();

        for(String cityName: cityNames)
        {
            ExecutorService executorService = Executors.newCachedThreadPool();

            Callable task = new WeatherServiceCallable(cityName);

            Future<WeatherInfoDTO> future = executorService.submit(task);

            futureList.add(future);
        }

        List<WeatherInfoDTO> weatherInfoDTOS = new ArrayList<>();

        for (Future<WeatherInfoDTO> weatherInfoDTOFuture: futureList)
        {
            WeatherInfoDTO finishedTask = weatherInfoDTOFuture.get();
            weatherInfoDTOS.add(finishedTask);
        }

        return weatherInfoDTOS;
    }
}
