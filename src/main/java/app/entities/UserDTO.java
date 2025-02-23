package app.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Builder
@Entity
public class UserDTO
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    private float weight;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "users",cascade = CascadeType.ALL)
    @Setter
    private Set<ActivityDTO> activities = new HashSet<>();

    public void addActivity(ActivityDTO activity)
    {
        if(activity != null){
            activities.add(activity);
            activity.setUsers(this);
        }
    }
}
