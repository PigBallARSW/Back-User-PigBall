package co.edu.eci.pigball.user.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Nullable
    @Size(min = 3, max = 30, message = "The username must be between 3 and 30 characters long.")
    @Pattern(regexp = "^(?!\\s)(?!.*\\s$)(?!.*\\s{2})\\S+( \\S+)*$", message = "The username cannot have spaces at the beginning, at the end, or consecutive spaces.")
    private String username;
    private String image;

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "The border color must be in hexadecimal format #RRGGBB.")
    private String borderColor;

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "The center color must be in hexadecimal format #RRGGBB.")
    private String centerColor;

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "The icon color must be in hexadecimal format #RRGGBB.")
    private String iconColor; // Color del icono

    private String iconType;
}