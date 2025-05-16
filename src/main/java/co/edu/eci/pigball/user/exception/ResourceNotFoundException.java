package co.edu.eci.pigball.user.exception;

import lombok.Getter;


@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final String className;
    private final String atributeName;
    private final String fieldName;

    public ResourceNotFoundException(String className, String atributeName, String fieldName) {
        super(String.format("%s not found with '%s' : '%s'", className, atributeName, fieldName));
        this.className = className;
        this.atributeName = atributeName;
        this.fieldName = fieldName;

    }
}
