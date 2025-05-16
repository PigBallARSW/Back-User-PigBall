package co.edu.eci.pigball.TestException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import co.edu.eci.pigball.user.exception.DuplicateResourceException;

class DuplicateResourceExceptionTest {

    @Test
    void testExceptionCreation() {
        // Arrange
        String className = "User";
        String attributeName = "name";
        String fieldName = "jags";
        String expectedMessage = "User already exists with 'name' : 'jags'";

        // Act
        DuplicateResourceException exception = new DuplicateResourceException(
                className, attributeName, fieldName);

        // Assert
        assertEquals(className, exception.getClassName());
        assertEquals(attributeName, exception.getAtributeName());
        assertEquals(fieldName, exception.getFieldName());
        assertEquals(expectedMessage, exception.getMessage());

    }
}
