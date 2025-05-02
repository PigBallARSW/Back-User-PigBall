package co.edu.eci.pigball.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
/* import jakarta.validation.constraints.Pattern; */
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {

    @NotBlank(message = "El ID es obligatorio")
    private String id;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    @NotBlank(message = "La imagen es obligatoria")
    private String image;

    @NotNull(message = "El color del borde es obligatorio")
    private RGB borderColor;  // Color del borde

    @NotNull(message = "El color del centro es obligatorio")
    private RGB centerColor; 
}