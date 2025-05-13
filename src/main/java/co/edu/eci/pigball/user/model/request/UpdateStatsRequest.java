package co.edu.eci.pigball.user.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatsRequest {
    private List<Stat> stats;
    private List<PlayerDTO> players;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Stat {
        private String first;
        private String second;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PlayerDTO {
        private String id;
        private String name;
        private String sessionId;
        private int team;
        private double x;
        private double y;
    }
}

