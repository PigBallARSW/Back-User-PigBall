package co.edu.eci.pigball.user.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RGB {

    @Min(value = 0, message = "El valor mínimo para red es 0")
    @Max(value = 255, message = "El valor máximo para red es 255")
    private int red;

    @Min(value = 0, message = "El valor mínimo para green es 0")
    @Max(value = 255, message = "El valor máximo para green es 255")
    private int green;

    @Min(value = 0, message = "El valor mínimo para blue es 0")
    @Max(value = 255, message = "El valor máximo para blue es 255")
    private int blue;
}