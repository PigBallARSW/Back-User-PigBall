package co.edu.eci.pigball.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String username;
    private int gamesPlayed;
    private int lostGames;
    private int gamesWon;
    private int totalScore;
    private double winningPercentage;
    private int bestScore;
}