package co.edu.eci.pigball.user.exception;

import lombok.Getter;

@Getter
public class DuplicateResourceException extends RuntimeException{
    
    private final String className;
    private final String atributeName;
    private final String fieldName;
    

    public DuplicateResourceException(String className, String atributeName, String fieldName) {
        super(String.format("%s already exists with '%s' : '%s'", className, atributeName ,fieldName));
        this.className = className;
        this.atributeName = atributeName;
        this.fieldName = fieldName;
        
    }
}
