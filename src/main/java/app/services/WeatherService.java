package app.services;

import app.entities.WeatherInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import app.dtos.WeatherInfoDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherService
{

    public static WeatherInfoDTO fetchWeatherDataByLocationName(String cityName)
    {
        HttpResponse<String> response;
        ObjectMapper objectMapper = new ObjectMapper();

        String url = "https://wttr.in/%%?format=j1";
        String uri = url.replace("%%", cityName);

        try
        {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(new URI(uri))
                    .GET()
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200)
            {
                JsonNode rootNode = objectMapper.readTree(response.body());

                // Extract city name from nearest_area -> areaName -> value
                cityName = rootNode.path("nearest_area")
                        .get(0) // Get the first element in the nearest_area array
                        .path("areaName")
                        .get(0) // Get the first area name
                        .path("value")
                        .asText(); // Get the city name as text

                // Extract current condition data
                JsonNode currentConditionNode = rootNode.path("current_condition").get(0); // First element of current_condition array
                int temp_C = currentConditionNode.path("temp_C").asInt(); // Convert temp_C (string) to int
                int cloudcover = currentConditionNode.path("cloudcover").asInt(); // Convert cloudcover (string) to int
                int humidity = currentConditionNode.path("humidity").asInt(); // Convert humidity (string) to int
                int windspeedKmph = currentConditionNode.path("windspeedKmph").asInt(); // Convert windspeedKmph (string) to int

                // Create WeatherInfoDTO and return it
                return new WeatherInfoDTO(cityName, temp_C, cloudcover, humidity, windspeedKmph);

            } else
            {
                System.out.println("GET request failed. Status code: " + response.statusCode());
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public WeatherInfo buildWeatherInfo(WeatherInfoDTO weatherInfoDTO)
    {

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
