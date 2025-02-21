package app.entities;

import app.dtos.CurrentDataDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class WeatherInfo
{
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column(name="location_name")
    private String locationName;

    @OneToOne(mappedBy = "weatherInfo", cascade = CascadeType.ALL)
    private CurrentData currentData;

    @OneToOne(mappedBy = "weatherInfo", cascade = CascadeType.ALL) // One activity has one weather report
    private Activity activity;
}
