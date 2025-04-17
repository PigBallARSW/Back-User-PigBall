package co.edu.eci.pigball.user.dto;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
    private List<String> StackTrace;
}
