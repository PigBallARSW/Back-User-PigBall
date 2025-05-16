package co.edu.eci.pigball.TestException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import co.edu.eci.pigball.user.exception.ResourceNotFoundException;

class ResourceNotFoundExceptionTest {

    @Test
    void testExceptionCreation() {
        // Arrange
        String className = "User";
        String attributeName = "id";
        String fieldName = "123";
        String expectedMessage = "User not found with 'id' : '123'";

        // Act
        ResourceNotFoundException exception = new ResourceNotFoundException(
                className, attributeName, fieldName);

        // Assert
        assertEquals(className, exception.getClassName());
        assertEquals(attributeName, exception.getAtributeName());
        assertEquals(fieldName, exception.getFieldName());
        assertEquals(expectedMessage, exception.getMessage());
    }

}
