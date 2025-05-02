package co.edu.eci.pigball.user.dto;

import jakarta.validation.constraints.Pattern;
// UpdateUserDTO.java
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {
    private String username;
    private String image;

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "El borderColor debe estar en formato hexadecimal #RRGGBB")
    private String borderColor;

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "El centerColor debe estar en formato hexadecimal #RRGGBB")
    private String centerColor;

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "El iconColor debe estar en formato hexadecimal #RRGGBB")
    private String iconColor; // Color del icono

    private String iconType;
}