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

    public List<CityInfoDTO> getCityInfoDTOs(String[] cityNames) throws ExecutionException, InterruptedException
    {
        List<Future<CityInfoDTO>> futureList = new ArrayList<>();

        ExecutorService executorService = Executors.newCachedThreadPool();

        for(String cityName: cityNames)
        {
            Callable task = new CityServiceCallable(cityName);

            Future<CityInfoDTO> future = executorService.submit(task);

            futureList.add(future);
        }

        List<CityInfoDTO> cityInfoDTOS = new ArrayList<>();

        for (Future<CityInfoDTO> cityInfoDTOFuture: futureList)
        {
            CityInfoDTO finishedTask = cityInfoDTOFuture.get();
            cityInfoDTOS.add(finishedTask);
        }

        return cityInfoDTOS;
    }
}
