package co.edu.eci.pigball.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class UserSummaryDTO {
    private String id;
    private String username;
    private int gamesWon;
    private String image;
    private String iconType;
    private String borderColor;
    private String centerColor; 
    private String iconColor;  
}
