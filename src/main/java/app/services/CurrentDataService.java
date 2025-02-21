package app.services;

import app.dtos.CurrentDataDTO;
import app.entities.CurrentData;

public class CurrentDataService
{
    //Task 3
    //Could this be void?
    public CurrentData persistCurrentData(CurrentDataDTO currentDataDTO){
        CurrentData currentData = CurrentData.builder()
                .temperature(currentDataDTO.getTemperature())
                .humidity(currentDataDTO.getHumidity())
                .skyText(currentDataDTO.getSkyText())
                .windText(currentDataDTO.getWindText())
                .build();
        return currentData;
    }
}
