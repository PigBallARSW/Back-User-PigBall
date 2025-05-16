package co.edu.eci.pigball.user.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BlogAppException extends RuntimeException{
    private final HttpStatus status;
    private final String message;

    public BlogAppException(HttpStatus status, String message) {
        super(message);
        this.message = message;
        this.status = status;
    }
}