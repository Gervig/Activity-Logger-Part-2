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
    @Column(nullable=false, unique = true)
    private String name;
    private String url;
    @Column(name="href_id")
    private String hrefId;
    private Double latitude;
    private Double longitude;

    @OneToOne(mappedBy = "cityInfo", cascade = CascadeType.ALL)
    private Activity activity;
}
