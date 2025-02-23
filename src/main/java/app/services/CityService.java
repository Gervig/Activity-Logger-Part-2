package app.services;

import app.entities.CityInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import app.dtos.CityInfoDTO;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class CityService
{

    private static final String BASE_URL = "https://api.dataforsyningen.dk/steder";

    public static CityInfoDTO getCityInfo(String cityName) throws IOException {
        // Build the API request URL
        String encodedProperty = URLEncoder.encode("primærtnavn", StandardCharsets.UTF_8.toString());
        String encodedCityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8.toString());
        String encodedURL = String.format("%s?%s=%s&hovedtype=Bebyggelse&undertype=by", BASE_URL, encodedProperty, encodedCityName);

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest
                    .newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .uri(new URI(encodedURL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check if the response is successful (status code 200)
            if (response.statusCode() == HttpURLConnection.HTTP_OK) {
                // Parse the response body as JSON into a CityInfoDTO
                ObjectMapper objectMapper = new ObjectMapper();

                // Assuming the response is an array and we want the first element
                CityInfoDTO[] cityInfoArray = objectMapper.readValue(response.body(), CityInfoDTO[].class);

                if (cityInfoArray.length > 0) {
                    return cityInfoArray[0]; // Return the first city info object
                } else {
                    throw new IOException("No city information found for: " + cityName);
                }
            } else {
                throw new IOException("Failed to fetch data from API. HTTP status code: " + response.statusCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CityInfo buildCityInfo(CityInfoDTO cityInfoDTO){
        CityInfo cityInfo = CityInfo.builder()
                .name(cityInfoDTO.getName())
                .url(cityInfoDTO.getUrl())
                .latitude(cityInfoDTO.getVisualCenter().get(0))
                .longitude(cityInfoDTO.getVisualCenter().get(1))
                .build();

        return cityInfo;
    }

}

