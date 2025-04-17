package co.edu.eci.pigball.user.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BlogAppException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public BlogAppException(HttpStatus status, String message) {
        super(message);
        this.message = message;
        this.status = status;
    }
}