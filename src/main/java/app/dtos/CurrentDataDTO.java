package app.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CurrentDataDTO {
    private double temperature;
    private String skyText;
    private int humidity;
    private String windText;
}