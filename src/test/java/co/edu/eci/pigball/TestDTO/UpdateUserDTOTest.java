package co.edu.eci.pigball.TestDTO;


import org.junit.jupiter.api.Test;

import co.edu.eci.pigball.user.dto.UpdateUserDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class UpdateUserDTOTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testBuilderAndGetters() {
        // Arrange
        String expectedUsername = "testUser";
        String expectedImage = "profile.jpg";
        String expectedBorderColor = "#FF0000";
        String expectedCenterColor = "#00FF00";
        String expectedIconColor = "#0000FF";
        String expectedIconType = "star";

        // Act
        UpdateUserDTO dto = UpdateUserDTO.builder()
                .username(expectedUsername)
                .image(expectedImage)
                .borderColor(expectedBorderColor)
                .centerColor(expectedCenterColor)
                .iconColor(expectedIconColor)
                .iconType(expectedIconType)
                .build();

        // Assert
        assertAll(
                () -> assertEquals(expectedUsername, dto.getUsername()),
                () -> assertEquals(expectedImage, dto.getImage()),
                () -> assertEquals(expectedBorderColor, dto.getBorderColor()),
                () -> assertEquals(expectedCenterColor, dto.getCenterColor()),
                () -> assertEquals(expectedIconColor, dto.getIconColor()),
                () -> assertEquals(expectedIconType, dto.getIconType())
        );
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        UpdateUserDTO dto = new UpdateUserDTO();

        // Assert
        assertNull(dto.getUsername());
        assertNull(dto.getImage());
        assertNull(dto.getBorderColor());
        assertNull(dto.getCenterColor());
        assertNull(dto.getIconColor());
        assertNull(dto.getIconType());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String expectedUsername = "allArgsUser";
        String expectedImage = "allargs.jpg";
        String expectedBorderColor = "#123456";
        String expectedCenterColor = "#654321";
        String expectedIconColor = "#ABCDEF";
        String expectedIconType = "medal";

        // Act
        UpdateUserDTO dto = new UpdateUserDTO(
                expectedUsername, expectedImage, expectedBorderColor,
                expectedCenterColor, expectedIconColor, expectedIconType
        );

        // Assert
        assertAll(
                () -> assertEquals(expectedUsername, dto.getUsername()),
                () -> assertEquals(expectedImage, dto.getImage()),
                () -> assertEquals(expectedBorderColor, dto.getBorderColor()),
                () -> assertEquals(expectedCenterColor, dto.getCenterColor()),
                () -> assertEquals(expectedIconColor, dto.getIconColor()),
                () -> assertEquals(expectedIconType, dto.getIconType())
        );
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        UpdateUserDTO dto = new UpdateUserDTO();
        String expectedUsername = "setterUser";
        String expectedImage = "setter.jpg";
        String expectedBorderColor = "#111111";
        String expectedCenterColor = "#222222";
        String expectedIconColor = "#333333";
        String expectedIconType = "trophy";

        // Act
        dto.setUsername(expectedUsername);
        dto.setImage(expectedImage);
        dto.setBorderColor(expectedBorderColor);
        dto.setCenterColor(expectedCenterColor);
        dto.setIconColor(expectedIconColor);
        dto.setIconType(expectedIconType);

        // Assert
        assertAll(
                () -> assertEquals(expectedUsername, dto.getUsername()),
                () -> assertEquals(expectedImage, dto.getImage()),
                () -> assertEquals(expectedBorderColor, dto.getBorderColor()),
                () -> assertEquals(expectedCenterColor, dto.getCenterColor()),
                () -> assertEquals(expectedIconColor, dto.getIconColor()),
                () -> assertEquals(expectedIconType, dto.getIconType())
        );
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        UpdateUserDTO dto1 = UpdateUserDTO.builder()
                .username("user1")
                .image("img1.jpg")
                .borderColor("#AAAAAA")
                .centerColor("#BBBBBB")
                .iconColor("#CCCCCC")
                .iconType("icon1")
                .build();

        UpdateUserDTO dto2 = UpdateUserDTO.builder()
                .username("user1")
                .image("img1.jpg")
                .borderColor("#AAAAAA")
                .centerColor("#BBBBBB")
                .iconColor("#CCCCCC")
                .iconType("icon1")
                .build();

        UpdateUserDTO differentDto = UpdateUserDTO.builder()
                .username("user2")
                .image("img2.jpg")
                .borderColor("#DDDDDD")
                .centerColor("#EEEEEE")
                .iconColor("#FFFFFF")
                .iconType("icon2")
                .build();

        // Assert
        assertAll(
                () -> assertEquals(dto1, dto2),
                () -> assertNotEquals(dto1, differentDto),
                () -> assertEquals(dto1.hashCode(), dto2.hashCode()),
                () -> assertNotEquals(dto1.hashCode(), differentDto.hashCode())
        );
    }

    @Test
    void testToString() {
        // Arrange
        UpdateUserDTO dto = UpdateUserDTO.builder()
                .username("testUser")
                .image("test.jpg")
                .borderColor("#FF0000")
                .centerColor("#00FF00")
                .iconColor("#0000FF")
                .iconType("testIcon")
                .build();

        // Act
        String result = dto.toString();

        // Assert
        assertAll(
                () -> assertTrue(result.contains("testUser")),
                () -> assertTrue(result.contains("test.jpg")),
                () -> assertTrue(result.contains("#FF0000")),
                () -> assertTrue(result.contains("#00FF00")),
                () -> assertTrue(result.contains("#0000FF")),
                () -> assertTrue(result.contains("testIcon"))
        );
    }

    @Test
    void testUsernameValidation() {
        // Arrange
        UpdateUserDTO validDto = UpdateUserDTO.builder()
                .username("valid User")
                .build();

        UpdateUserDTO invalidDto1 = UpdateUserDTO.builder()
                .username("  invalidStart")
                .build();

        UpdateUserDTO invalidDto2 = UpdateUserDTO.builder()
                .username("invalidEnd  ")
                .build();

        UpdateUserDTO invalidDto3 = UpdateUserDTO.builder()
                .username("too  many  spaces")
                .build();

        UpdateUserDTO invalidDto4 = UpdateUserDTO.builder()
                .username("ab") // Too short
                .build();

        // Act & Assert
        assertTrue(validator.validate(validDto).isEmpty());

        Set<ConstraintViolation<UpdateUserDTO>> violations1 = validator.validate(invalidDto1);
        assertEquals(1, violations1.size());
        assertEquals("The username cannot have spaces at the beginning, at the end, or consecutive spaces.", 
                violations1.iterator().next().getMessage());

        Set<ConstraintViolation<UpdateUserDTO>> violations2 = validator.validate(invalidDto2);
        assertEquals(1, violations2.size());

        Set<ConstraintViolation<UpdateUserDTO>> violations3 = validator.validate(invalidDto3);
        assertEquals(1, violations3.size());

        Set<ConstraintViolation<UpdateUserDTO>> violations4 = validator.validate(invalidDto4);
        assertEquals(1, violations4.size());
        assertEquals("The username must be between 3 and 30 characters long.", 
                violations4.iterator().next().getMessage());
    }

    @Test
    void testColorValidation() {
        // Arrange
        UpdateUserDTO validDto = UpdateUserDTO.builder()
                .borderColor("#123456")
                .centerColor("#ABCDEF")
                .iconColor("#abcdef")
                .build();

        UpdateUserDTO invalidDto1 = UpdateUserDTO.builder()
                .borderColor("123456") // Missing #
                .build();

        UpdateUserDTO invalidDto2 = UpdateUserDTO.builder()
                .centerColor("#GHIJKL") // Invalid chars
                .build();

        UpdateUserDTO invalidDto3 = UpdateUserDTO.builder()
                .iconColor("#12345") // Too short
                .build();

        // Act & Assert
        assertTrue(validator.validate(validDto).isEmpty());

        Set<ConstraintViolation<UpdateUserDTO>> violations1 = validator.validate(invalidDto1);
        assertEquals(1, violations1.size());
        assertEquals("The border color must be in hexadecimal format #RRGGBB.", 
                violations1.iterator().next().getMessage());

        Set<ConstraintViolation<UpdateUserDTO>> violations2 = validator.validate(invalidDto2);
        assertEquals(1, violations2.size());

        Set<ConstraintViolation<UpdateUserDTO>> violations3 = validator.validate(invalidDto3);
        assertEquals(1, violations3.size());
    }
}
