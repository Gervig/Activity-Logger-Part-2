package app.entities;

import app.dtos.CityInfoDTO;
import app.dtos.WeatherInfoDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Activity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "exercise_date")
    private LocalDate exerciseDate;
    @Column(name = "exercise_type")
    private Activity exerciseType;
    @Column(name = "time_of_day")
    private LocalTime timeOfDay;
    private double duration;
    private double distance;
    private String comment;

    @ManyToOne
    @Setter
    private User user;

    public void addUser(User user){
        if(user != null){
            this.user = user;
            user.addActivity(this);
        }
    }

    //PersonDetail -> Activity
    @OneToOne
    @MapsId
    @ToString.Exclude
    @Column(name = "city_info_id")
    private CityInfo cityInfo;

    @OneToOne
    @MapsId
    @ToString.Exclude
    @Column(name = "weather_info_id")
    private WeatherInfoDTO weatherInfo;

}
