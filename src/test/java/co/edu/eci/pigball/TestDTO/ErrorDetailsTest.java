package co.edu.eci.pigball.TestDTO;

import org.junit.jupiter.api.Test;

import co.edu.eci.pigball.user.dto.ErrorDetails;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ErrorDetailsTest {

    @Test
    void testBuilderAndGetters() {
        // Arrange
        Date expectedTimestamp = new Date();
        String expectedMessage = "Error message";
        String expectedDetails = "Error details";
        List<String> expectedStackTrace = Arrays.asList("line1", "line2", "line3");

        // Act
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(expectedTimestamp)
                .message(expectedMessage)
                .details(expectedDetails)
                .stackTrace(expectedStackTrace)
                .build();

        // Assert
        assertAll(
                () -> assertEquals(expectedTimestamp, errorDetails.getTimestamp()),
                () -> assertEquals(expectedMessage, errorDetails.getMessage()),
                () -> assertEquals(expectedDetails, errorDetails.getDetails()),
                () -> assertEquals(expectedStackTrace, errorDetails.getStackTrace()));
    }

    @Test
    void testSetters() {
        // Arrange
        ErrorDetails errorDetails = ErrorDetails.builder().build();
        Date expectedTimestamp = new Date();
        String expectedMessage = "New error message";
        String expectedDetails = "New error details";
        List<String> expectedStackTrace = Arrays.asList("new line1", "new line2");

        // Act
        errorDetails.setTimestamp(expectedTimestamp);
        errorDetails.setMessage(expectedMessage);
        errorDetails.setDetails(expectedDetails);
        errorDetails.setStackTrace(expectedStackTrace);

        // Assert
        assertAll(
                () -> assertEquals(expectedTimestamp, errorDetails.getTimestamp()),
                () -> assertEquals(expectedMessage, errorDetails.getMessage()),
                () -> assertEquals(expectedDetails, errorDetails.getDetails()),
                () -> assertEquals(expectedStackTrace, errorDetails.getStackTrace()));
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Date timestamp = new Date();
        List<String> stackTrace = Arrays.asList("trace1", "trace2");

        ErrorDetails errorDetails1 = ErrorDetails.builder()
                .timestamp(timestamp)
                .message("Error")
                .details("Details")
                .stackTrace(stackTrace)
                .build();

        ErrorDetails errorDetails2 = ErrorDetails.builder()
                .timestamp(timestamp)
                .message("Error")
                .details("Details")
                .stackTrace(stackTrace)
                .build();

        ErrorDetails differentErrorDetails = ErrorDetails.builder()
                .timestamp(new Date(timestamp.getTime() + 1000))
                .message("Different Error")
                .details("Different Details")
                .stackTrace(Arrays.asList("different trace"))
                .build();

        // Assert
        assertAll(
                () -> assertEquals(errorDetails1, errorDetails2),
                () -> assertNotEquals(errorDetails1, differentErrorDetails),
                () -> assertEquals(errorDetails1.hashCode(), errorDetails2.hashCode()),
                () -> assertNotEquals(errorDetails1.hashCode(), differentErrorDetails.hashCode()));
    }

    @Test
    void testToString() {
        // Arrange
        Date timestamp = new Date();
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(timestamp)
                .message("Test message")
                .details("Test details")
                .stackTrace(Arrays.asList("trace1", "trace2"))
                .build();

        // Act
        String toStringResult = errorDetails.toString();

        // Assert
        assertAll(
                () -> assertTrue(toStringResult.contains("Test message")),
                () -> assertTrue(toStringResult.contains("Test details")),
                () -> assertTrue(toStringResult.contains("trace1")),
                () -> assertTrue(toStringResult.contains(timestamp.toString())));
    }

    // Pruebas adicionales para equals()
        @Test
        void testEquals_SameInstance_ReturnsTrue() {
        ErrorDetails error = ErrorDetails.builder().message("Error").build();
        assertEquals(error, error);
        }

        @Test
        void testEquals_NullComparison_ReturnsFalse() {
        ErrorDetails error = ErrorDetails.builder().details("Details").build();
        assertNotEquals(null, error);
        }

        @Test
        void testEquals_DifferentClass_ReturnsFalse() {
        ErrorDetails error = ErrorDetails.builder().build();
        assertNotEquals( "Not an ErrorDetails object",error);
        }

        @Test
        void testEquals_DifferentTimestamp_NotEqual() {
        Date now = new Date();
        Date later = new Date(now.getTime() + 1000);
        
        ErrorDetails error1 = ErrorDetails.builder().timestamp(now).build();
        ErrorDetails error2 = ErrorDetails.builder().timestamp(later).build();
        
        assertNotEquals(error1, error2);
        }

        @Test
        void testEquals_DifferentMessage_NotEqual() {
        ErrorDetails error1 = ErrorDetails.builder().message("Error 1").build();
        ErrorDetails error2 = ErrorDetails.builder().message("Error 2").build();
        
        assertNotEquals(error1, error2);
        }

        @Test
        void testEquals_DifferentDetails_NotEqual() {
        ErrorDetails error1 = ErrorDetails.builder().details("Detail 1").build();
        ErrorDetails error2 = ErrorDetails.builder().details("Detail 2").build();
        
        assertNotEquals(error1, error2);
        }

        @Test
        void testEquals_DifferentStackTrace_NotEqual() {
        List<String> trace1 = Arrays.asList("trace1", "trace2");
        List<String> trace2 = Arrays.asList("trace3", "trace4");
        
        ErrorDetails error1 = ErrorDetails.builder().stackTrace(trace1).build();
        ErrorDetails error2 = ErrorDetails.builder().stackTrace(trace2).build();
        
        assertNotEquals(error1, error2);
        }

        @Test
        void testEquals_NullVsNonNullFields_NotEqual() {
        ErrorDetails error1 = ErrorDetails.builder().message(null).build();
        ErrorDetails error2 = ErrorDetails.builder().message("Exists").build();
        
        assertNotEquals(error1, error2);
        }

        @Test
        void testEquals_SameDateDifferentInstance_Equal() {
        Date date = new Date();
        ErrorDetails error1 = ErrorDetails.builder().timestamp(date).build();
        ErrorDetails error2 = ErrorDetails.builder().timestamp(new Date(date.getTime())).build();
        
        assertEquals(error1, error2);
        }

        // Pruebas para hashCode()
        @Test
        void testHashCode_Consistency_ShouldMatch() {
        ErrorDetails error = ErrorDetails.builder()
                .message("Consistency test")
                .stackTrace(Arrays.asList("trace"))
                .build();
        
        int initialHash = error.hashCode();
        assertEquals(initialHash, error.hashCode());
        }

        @Test
        void testHashCode_WithNullFields_DoesNotThrow() {
        ErrorDetails error = ErrorDetails.builder()
                .timestamp(null)
                .message(null)
                .details(null)
                .stackTrace(null)
                .build();
        
        assertDoesNotThrow(error::hashCode);
        }

        @Test
        void testHashCode_DifferentStackTrace_DifferentHash() {
        List<String> trace1 = Arrays.asList("line1");
        List<String> trace2 = Arrays.asList("line2");
        
        ErrorDetails error1 = ErrorDetails.builder().stackTrace(trace1).build();
        ErrorDetails error2 = ErrorDetails.builder().stackTrace(trace2).build();
        
        assertNotEquals(error1.hashCode(), error2.hashCode());
        }

        @Test
        void testEquals_AllNullFields_Equal() {
        ErrorDetails error1 = ErrorDetails.builder().build();
        ErrorDetails error2 = ErrorDetails.builder().build();
        
        assertEquals(error1, error2);
        assertEquals(error1.hashCode(), error2.hashCode());
        }

        @Test
        void testEquals_OnlyOneFieldDifferent_NotEqual() {
        // Objeto base
        ErrorDetails base = ErrorDetails.builder()
                .timestamp(new Date())
                .message("Base")
                .details("Details")
                .stackTrace(Arrays.asList("trace"))
                .build();

        // Objeto con solo un campo diferente
        ErrorDetails modified = ErrorDetails.builder()
                .timestamp(base.getTimestamp())
                .message("Modified") // Único cambio
                .details(base.getDetails())
                .stackTrace(base.getStackTrace())
                .build();

        assertNotEquals(base, modified);
        }

        @Test
        void testErrorDetailsBuilderToString() {
        // Configurar valores en el builder
        Date testDate = new Date();
        ErrorDetails.ErrorDetailsBuilder builder = ErrorDetails.builder()
                .timestamp(testDate)
                .message("Error de prueba")
                .details("Detalles técnicos")
                .stackTrace(List.of("StackTraceLine1", "StackTraceLine2"));

        // Obtener representación String del builder
        String builderString = builder.toString();

        // Verificar que los campos configurados están presentes
        assertAll(
                () -> assertTrue(builderString.contains("timestamp=" + testDate), "Debe mostrar la fecha"),
                () -> assertTrue(builderString.contains("message=Error de prueba"), "Debe contener el mensaje"),
                () -> assertTrue(builderString.contains("details=Detalles técnicos"), "Debe mostrar detalles"),
                () -> assertTrue(builderString.contains("stackTrace=[StackTraceLine1, StackTraceLine2]"), "Debe mostrar stacktrace")
        );
        }
}
