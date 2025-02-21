package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    private float weight;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "activity",cascade = CascadeType.ALL)
    @Setter
    private Set<Activity> activities = new HashSet<>();

    public void addActivity(Activity activity)
    {
        if(activity != null){
            activities.add(activity);
            activity.setUser(this);
        }
    }
}
