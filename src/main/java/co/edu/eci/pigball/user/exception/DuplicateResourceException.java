package co.edu.eci.pigball.user.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuplicateResourceException extends RuntimeException{
    
    private String resourceName;
    private String fieldName;

    public DuplicateResourceException(String resourceName, String fieldName) {
        super(String.format("%s ya existe con el id : '%s'", resourceName, fieldName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
    }
}
