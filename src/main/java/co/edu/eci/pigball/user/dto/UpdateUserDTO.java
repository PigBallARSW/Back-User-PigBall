package co.edu.eci.pigball.user.dto;

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
    private RGB borderColor;  // Color del borde
    private RGB centerColor; 
}