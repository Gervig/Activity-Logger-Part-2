package app.entities;

import jakarta.persistence.*;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @OneToOne(mappedBy = "cityInfo", cascade = CascadeType.ALL)
    private Activity activity;
}
