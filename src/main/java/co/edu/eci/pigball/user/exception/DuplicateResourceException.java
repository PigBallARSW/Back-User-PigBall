package co.edu.eci.pigball.user.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuplicateResourceException extends RuntimeException{
    
    private String className;
    private String atributeName;
    private String fieldName;
    

    public DuplicateResourceException(String className, String atributeName, String fieldName) {
        super(String.format("%s already exists with '%s' : '%s'", className, atributeName ,fieldName));
        this.className = className;
        this.atributeName = atributeName;
        this.fieldName = fieldName;
        
    }
}
