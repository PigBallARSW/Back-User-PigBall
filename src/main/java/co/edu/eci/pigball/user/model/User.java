package co.edu.eci.pigball.user.model;



import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.eci.pigball.user.dto.RGB;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String image;

    private String iconType;

    private RGB borderColor;

    private RGB centerColor; 

    private RGB iconColor;  

    @Builder.Default
    private Set<String> friendsIds = new HashSet<>();

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

    public void addFriendId(String friendId) {
        if (friendId != null) {
            friendsIds.add(friendId);
        }
    }

    public void removeFriendId(String friendId) {
        if (friendId != null) {
            friendsIds.remove(friendId);
        }
    }
}
