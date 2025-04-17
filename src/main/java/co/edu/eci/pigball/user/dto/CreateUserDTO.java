package co.edu.eci.pigball.user.dto;

import jakarta.validation.constraints.NotBlank;
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
/*     @Pattern(
        regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",
        message = "Formato de ID inválido (debe ser UUID v4)"
    ) */
    @NotBlank(message = "El ID es obligatorio")
    private String id;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;
}