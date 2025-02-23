package app.callables;

import app.dtos.CityInfoDTO;
import app.services.CityService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CityServiceCallable implements Callable<CityInfoDTO>
{
    String cityName;

    public CityServiceCallable(String cityName)
    {
        this.cityName = cityName;
    }

    @Override
    public CityInfoDTO call() throws Exception
    {
        CityInfoDTO cityInfoDTO = CityService.getCityInfo(cityName);
        return cityInfoDTO;
    }

    public static List<CityInfoDTO> getCityInfoDTOs(String[] cityNames)
    {
        List<Future<CityInfoDTO>> futureList = new ArrayList<>();
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (String cityName : cityNames)
        {
            Callable<CityInfoDTO> task = new CityServiceCallable(cityName);
            Future<CityInfoDTO> future = executorService.submit(task);
            futureList.add(future);
        }

        List<CityInfoDTO> cityInfoDTOS = new ArrayList<>();

        for (Future<CityInfoDTO> cityInfoDTOFuture : futureList)
        {
            try
            {
                CityInfoDTO finishedTask = cityInfoDTOFuture.get();
                cityInfoDTOS.add(finishedTask);
            } catch (InterruptedException | ExecutionException e)
            {
                System.err.println("Error retrieving data for a city: " + e.getMessage());
                e.printStackTrace();
            }
        }

        executorService.shutdown();
        return cityInfoDTOS;
    }

}
