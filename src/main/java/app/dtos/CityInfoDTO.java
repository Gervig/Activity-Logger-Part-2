package app.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.*;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityInfoDTO {
    @JsonSetter("primærtnavn")
    private String name;
    @JsonSetter("egenskaber")
    private Properties properties;
    @JsonSetter("visueltcenter")
    private List<Double> visualCenter;
    @JsonSetter("kommuner")
    private List<Municipality> municipalities;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Properties {
        @JsonSetter("bebyggelseskode")
        private int buildingCode;
        @JsonSetter("indbyggerantal")
        private Integer population;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Municipality {
        @JsonSetter("kode")
        private int code;
        @JsonSetter("navn")
        private String name;
    }
}
