package app.services;

import app.entities.WeatherInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import app.dtos.WeatherInfoDTO;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherService {

    public static WeatherInfoDTO fetchWeatherDataByLocationName(String cityName) {
        HttpResponse<String> response;
        ObjectMapper objectMapper = new ObjectMapper();

        String url = "https://wttr.in/%%?format=j1";
        String uri = url.replace("%%", cityName);

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(new URI(uri))
                    .GET()
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode rootNode = objectMapper.readTree(response.body());
            cityName = rootNode.path("nearest_area")
                    .get(0)    // Get the first element from the array
                    .path("areaName")
                    .get(0)    // Get the first area name
                    .path("value")
                    .asText(); // Get the city name as text

            if (response.statusCode() == 200) {
                WeatherInfoDTO weatherInfoDTO = objectMapper.readValue(response.body(), WeatherInfoDTO.class);
                weatherInfoDTO.setCityName(cityName);
                return weatherInfoDTO;
            } else {
                System.out.println("GET request failed. Status code: " + response.statusCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public WeatherInfo buildWeatherInfo(WeatherInfoDTO weatherInfoDTO){

        WeatherInfo weatherInfo = WeatherInfo.builder()
                .cityName(weatherInfoDTO.getCityName())
                .temp_C(weatherInfoDTO.getTemp_C())
                .cloudcover(weatherInfoDTO.getCloudcover())
                .humidity(weatherInfoDTO.getHumidity())
                .windspeedKmph(weatherInfoDTO.getWindspeedKmph())
                .build();

        return weatherInfo;
    }
}
