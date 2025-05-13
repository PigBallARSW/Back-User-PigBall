package co.edu.eci.pigball.TestException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import co.edu.eci.pigball.user.exception.DuplicateResourceException;

public class DuplicateResourceExceptionTest {

    @Test
    public void testExceptionCreation() {
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

        String nclassName = "User1";
        String nattributeName = "name1";
        String nfieldName = "jags1";

        exception.setClassName(nclassName);
        exception.setAtributeName(nattributeName);
        exception.setFieldName(nfieldName);
        assertEquals(nclassName, exception.getClassName());
        assertEquals(nattributeName, exception.getAtributeName());
        assertEquals(nfieldName, exception.getFieldName());
    }
}
