package co.edu.eci.pigball.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private String id;
    private String username;
    private int gamesPlayed;
    private int lostGames;
    private int gamesWon;
    private int totalScore;
    private double winningPercentage;
    private int bestScore;
    private String image;
    private String borderColor;  
    private String centerColor;
    private String iconColor;
    private String iconType;
    private RGB borderColor;  
    private RGB centerColor;
    private RGB iconColor;

}