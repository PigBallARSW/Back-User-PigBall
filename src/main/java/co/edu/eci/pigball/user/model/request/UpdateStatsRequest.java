package co.edu.eci.pigball.user.model.request;

import lombok.Data;
import lombok.Getter;

import java.util.List;
@Getter
public class UpdateStatsRequest {
    private List<Stat> stats;
    private List<PlayerDTO> players;

    @Data
    public static class Stat {
        private String first;
        private String second;
    }

    @Data
    public static class PlayerDTO {
        private String id;
        private String name;
        private String sessionId;
        private int team;
        private double x;
        private double y;
    }
}

