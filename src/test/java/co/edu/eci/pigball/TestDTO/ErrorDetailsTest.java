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
                .StackTrace(expectedStackTrace)
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
                .StackTrace(stackTrace)
                .build();

        ErrorDetails errorDetails2 = ErrorDetails.builder()
                .timestamp(timestamp)
                .message("Error")
                .details("Details")
                .StackTrace(stackTrace)
                .build();

        ErrorDetails differentErrorDetails = ErrorDetails.builder()
                .timestamp(new Date(timestamp.getTime() + 1000))
                .message("Different Error")
                .details("Different Details")
                .StackTrace(Arrays.asList("different trace"))
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
                .StackTrace(Arrays.asList("trace1", "trace2"))
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
}
