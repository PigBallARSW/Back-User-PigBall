package co.edu.eci.pigball.user.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
    private String className;
    private String atributeName;
    private String fieldName;

    public ResourceNotFoundException(String className, String atributeName, String fieldName) {
        super(String.format("%s not found with '%s' : '%s'", className, atributeName, fieldName));
        this.className = className;
        this.atributeName = atributeName;
        this.fieldName = fieldName;

    }
}
