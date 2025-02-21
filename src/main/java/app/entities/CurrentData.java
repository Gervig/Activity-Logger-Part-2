package app.entities;

import jakarta.persistence.Column;

public class CurrentData
{
    private double temperature;
    @Column(name="sky_text")
    private String skyText;
    private int humidity;
    @Column(name="wind_text")
    private String windText;
}
