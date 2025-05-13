package co.edu.eci.pigball.TestException;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.validation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import co.edu.eci.pigball.user.dto.ErrorDetails;
import co.edu.eci.pigball.user.exception.*;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;
    
    private final WebRequest webRequest = mock(WebRequest.class);

    // ------------------------- Pruebas para ResourceNotFoundException -------------------------
    @Test
    public void testHandleResourceNotFoundException() {
        ResourceNotFoundException ex = new ResourceNotFoundException("User", "id", "123");
        when(webRequest.getDescription(false)).thenReturn("details");

        ResponseEntity<ErrorDetails> response = exceptionHandler.handleResourceNotFoundExcepction(ex, webRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertErrorDetails(response.getBody(), ex.getMessage(), "details");
    }

    // ------------------------- Pruebas para BlogAppException -------------------------
    @Test
    public void testHandleBlogAppException() {
        BlogAppException ex = new BlogAppException(HttpStatus.BAD_REQUEST, "Error de prueba");
        when(webRequest.getDescription(false)).thenReturn("details");

        ResponseEntity<ErrorDetails> response = exceptionHandler.handleBlogAppException(ex, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertErrorDetails(response.getBody(), ex.getMessage(), "details");
    }

    // ------------------------- Pruebas para DuplicateResourceException -------------------------
    @Test
    public void testHandleDuplicateResourceException() {
        DuplicateResourceException ex = new DuplicateResourceException("User", "email", "user@test.com");
        when(webRequest.getDescription(false)).thenReturn("details");

        ResponseEntity<ErrorDetails> response = exceptionHandler.handleDuplicateUserIdException(ex, webRequest);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertErrorDetails(response.getBody(), ex.getMessage(), "details");
    }

    // ------------------------- Pruebas para Exception global -------------------------
    @Test
    public void testHandleGlobalException() {
        Exception ex = new RuntimeException("Error inesperado");
        when(webRequest.getDescription(true)).thenReturn("details_with_trace");

        ResponseEntity<ErrorDetails> response = exceptionHandler.handleGlobalException(ex, webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertErrorDetails(response.getBody(), ex.getMessage(), "details_with_trace");
    }

    // ------------------------- Pruebas para Validación de Argumentos -------------------------
    @Test
        void testHandleMethodArgumentNotValid() {
            // Arrange
            BindingResult bindingResult = mock(BindingResult.class);
            MethodArgumentNotValidException ex = new MethodArgumentNotValidException(
                null, 
                bindingResult
            );

            // Configurar errores de validación
            when(bindingResult.getAllErrors()).thenReturn(Arrays.asList(
                new FieldError("userDTO", "username", "Username is required"),
                new FieldError("userDTO", "email", "Email must be valid")
            ));

            // Act
            ResponseEntity<Object> response = exceptionHandler.handleMethodArgumentNotValid(
                ex, 
                new HttpHeaders(), 
                HttpStatus.BAD_REQUEST, 
                webRequest
            );

            // Assert
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            @SuppressWarnings("unchecked")
            Map<String, String> errors = (Map<String, String>) response.getBody();
            assertEquals("Username is required", errors.get("username"));
            assertEquals("Email must be valid", errors.get("email"));
        }

    // ------------------------- Métodos de Soporte -------------------------
    private void assertErrorDetails(ErrorDetails errorDetails, String expectedMessage, String expectedDetails) {
        assertNotNull(errorDetails);
        assertEquals(expectedMessage, errorDetails.getMessage());
        assertEquals(expectedDetails, errorDetails.getDetails());
        assertNotNull(errorDetails.getTimestamp());
    }
}