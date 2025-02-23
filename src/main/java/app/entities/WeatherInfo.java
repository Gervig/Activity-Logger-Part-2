package app.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Builder
@Entity
public class WeatherInfo
{
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column(name="city_name")
    private String cityName;
    private int temp_C;
    private int cloudcover;
    private int humidity;
    private int windspeedKmph;

    @OneToOne(mappedBy = "weatherInfo", cascade = CascadeType.ALL)
    private Activity activity;
}
