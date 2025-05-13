package co.edu.eci.pigball.TestException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import co.edu.eci.pigball.user.exception.ResourceNotFoundException;

public class ResourceNotFoundExceptionTest {

    @Test
    public void testExceptionCreation() {
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

    @Test
    public void testSettersUpdateFieldsAndMessage() {
        // Arrange initial exception
        ResourceNotFoundException exception = new ResourceNotFoundException(
                "User", "id", "123");

        // New values
        String newClass = "Order";
        String newAttribute = "orderId";
        String newField = "789";

        // Act: update via setters
        exception.setClassName(newClass);
        exception.setAtributeName(newAttribute);
        exception.setFieldName(newField);

        // Assert updated getters
        assertEquals(newClass, exception.getClassName());
        assertEquals(newAttribute, exception.getAtributeName());
        assertEquals(newField, exception.getFieldName());
        // Assert message reflects new values
    }
}
