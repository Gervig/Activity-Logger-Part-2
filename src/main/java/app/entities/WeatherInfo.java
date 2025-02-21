package app.entities;

import app.dtos.CurrentDataDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherInfo
{
    @Column(name="location_name")
    private String locationName;

    @OneToOne(mappedBy = "weatherInfo", cascade = CascadeType.ALL)
    private CurrentDataDTO currentData;

    @OneToOne(mappedBy = "weatherInfo", cascade = CascadeType.ALL)
    private Activity activity;
}
