package co.edu.eci.pigball.user.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldName;

    public ResourceNotFoundException(String resourceName,String fieldName) {
        super(String.format("%s no encotrado con id o nombre : '%s'", resourceName, fieldName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
    }
}


