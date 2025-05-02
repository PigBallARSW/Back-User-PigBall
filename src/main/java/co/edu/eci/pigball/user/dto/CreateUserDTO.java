package co.edu.eci.pigball.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank(message = "El iconColor es obligatorio")
    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "El iconColor debe estar en formato hexadecimal #RRGGBB")
    private String iconColor;

    @NotBlank(message = "El borderColor es obligatorio")
    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "El borderColor debe estar en formato hexadecimal #RRGGBB")
    private String borderColor;

    @NotBlank(message = "El centerColor es obligatorio")
    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "El centerColor debe estar en formato hexadecimal #RRGGBB")
    private String centerColor;

    @NotNull(message = "El tipo de icono es obligatorio")
    private String iconType;

}