package co.edu.eci.pigball.user.changeName;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id 
    private String id;
    
    private String username;
    
    @Builder.Default 
    private int lostGames = 0;
    
    @Builder.Default 
    private int gamesWon = 0;
    
    @Builder.Default 
    private int totalScore = 0;
    
    @Builder.Default 
    private int bestScore = 0;

    // --- Métodos de actualización ---
    public void incrementLostGames() {
        this.lostGames++;
    }

    public void incrementGamesWon() {
        this.gamesWon++;
    }

    public void addToTotalScore(int points) {
        if (points > 0) {
            this.totalScore += points;
        }
    }

    public void updateBestScore(int newScore) {
        if (newScore > this.bestScore) {
            this.bestScore = newScore;
        }
    }

    public int getGamesPlayed() {
        return lostGames + gamesWon;
    }

    public double getWinningPercentage() {
        return getGamesPlayed() > 0 ? (gamesWon * 100.0) / getGamesPlayed() : 0.0;
    }
}
