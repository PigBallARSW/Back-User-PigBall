package co.edu.eci.pigball.user.Model;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private int gamesPlayed;
    private int lostGames;
    private int gamesWon;
    private int totalScore;
    private double winningPercentage;
    private int bestScore;
}
