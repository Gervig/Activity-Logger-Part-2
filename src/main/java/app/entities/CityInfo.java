package app.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CityInfo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable=false, unique = true)
    private String name;

    @Setter
    private String url;

    @Setter
    @Column(name="href_id")
    private String hrefId;

    @Setter
    private Double latitude;

    @Setter
    private Double longitude;

    //TODO lav relation om, mange til en
    @OneToOne
    @JoinColumn(name = "activity_id") // This makes CityInfo store the foreign key
    private Activity activity;
}
