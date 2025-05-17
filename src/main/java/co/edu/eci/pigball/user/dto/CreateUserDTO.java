package co.edu.eci.pigball.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {

    @NotBlank(message = "The ID is required.")
    private String id;

    @NotBlank(message = "The username is required.")
    @Size(min = 3, max = 30, message = "The username must be between 3 and 30 characters long.")
    @Pattern(
        regexp = "^(?!\\s)(?!.*\\s$)(?!.*\\s{2})\\S+(?> \\S+)*$",
        message = "The username cannot have spaces at the beginning, at the end, or consecutive spaces."
    )
    private String username;

    @NotBlank(message = "The image is required.")
    private String image;

    @NotBlank(message = "The icon color is required.")
    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "The icon color must be in hexadecimal format #RRGGBB.")
    private String iconColor;

    @NotBlank(message = "The border color is required.")
    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "The border color must be in hexadecimal format #RRGGBB.")
    private String borderColor;

    @NotBlank(message = "The center color is required.")
    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "The center color must be in hexadecimal format #RRGGBB.")
    private String centerColor;

    @NotNull(message = "The icon type is required.")
    private String iconType;

}