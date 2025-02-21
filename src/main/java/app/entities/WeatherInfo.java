package app.entities;

import jakarta.persistence.CascadeType;
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
    //Person -> CityInfo
    @OneToOne(mappedBy = "weatherInfo", cascade = CascadeType.ALL)
    private Activity activity;
}
