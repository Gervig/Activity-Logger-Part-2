package app.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CurrentData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double temperature;

    @Column(name="sky_text")
    private String skyText;

    private int humidity;

    @Column(name="wind_text")
    private String windText;

    @OneToOne(mappedBy = "currentData")
    private WeatherInfo weatherInfo;
}
