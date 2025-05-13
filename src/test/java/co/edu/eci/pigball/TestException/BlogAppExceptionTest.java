package co.edu.eci.pigball.TestException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class BlogAppExceptionTest {

    @Test
    public void testExceptionCreation() {
        // Arrange
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = "Resource not found";

        // Act
        BlogAppException exception = new BlogAppException(status, message);

        // Assert
        assertEquals(status, exception.getStatus());
        assertEquals(message, exception.getMessage());
        assertEquals(message, exception.getSuperMessage());
    }

    private static class BlogAppException extends co.edu.eci.pigball.user.exception.BlogAppException {
        public BlogAppException(HttpStatus status, String message) {
            super(status, message);
        }

        // Método para acceder al mensaje de la superclase
        public String getSuperMessage() {
            return super.getMessage();
        }
    }
}
