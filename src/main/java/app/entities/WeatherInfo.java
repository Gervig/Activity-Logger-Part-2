package app.entities;

import app.dtos.CurrentDataDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Column(name="location_name")
    private String locationName;

    @OneToOne // WeatherInfo owns the relationship
    @JoinColumn(name = "current_data_id", nullable = false)
    private CurrentData currentData;

    @OneToOne(mappedBy = "weatherInfo", cascade = CascadeType.ALL)
    private Activity activity;
}
