package app.entities;

import app.enums.ActivityType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Activity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "exercise_date")
    private LocalDate exerciseDate;
    @Column(name = "exercise_type")
    private ActivityType exerciseType;
    @Column(name = "time_of_day")
    private LocalTime timeOfDay;
    private double duration;
    private double distance;
    private String comment;

    @ManyToOne
    @Setter
    private Users users;

    public void addUser(Users users){
        if(users != null){
            this.users = users;
            users.addActivity(this);
        }
    }

    @OneToOne(mappedBy = "activity")
    @ToString.Exclude
    private CityInfo cityInfo;

    @OneToOne(mappedBy = "activity")
    @ToString.Exclude
    private WeatherInfo weatherInfo;


}
