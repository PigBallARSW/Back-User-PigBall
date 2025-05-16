package co.edu.eci.pigball.TestDTO;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import co.edu.eci.pigball.user.dto.CreateUserDTO;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateUserDTOTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void closeValidator() {
        factory.close();
    }

    @Test
    void whenAllFieldsValid_thenNoViolations() {
        CreateUserDTO dto = CreateUserDTO.builder()
                .id("123")
                .username("ValidUser")
                .image("image.png")
                .iconColor("#A1B2C3")
                .borderColor("#abcdef")
                .centerColor("#123456")
                .iconType("circle")
                .build();

        Set<ConstraintViolation<CreateUserDTO>> violations = validator.validate(dto);
        assertEquals(0, violations.size());
    }

    @Test
    void whenIdBlank_thenViolation() {
        CreateUserDTO dto = CreateUserDTO.builder()
                .id("  ")
                .username("User")
                .image("img.png")
                .iconColor("#FFFFFF")
                .borderColor("#000000")
                .centerColor("#112233")
                .iconType("square")
                .build();

        Set<ConstraintViolation<CreateUserDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("The ID is required.", violations.iterator().next().getMessage());
    }

    @Test
    void whenUsernameTooShort_thenViolation() {
        CreateUserDTO dto = CreateUserDTO.builder()
                .id("1")
                .username("ab")
                .image("img.png")
                .iconColor("#FFFFFF")
                .borderColor("#000000")
                .centerColor("#112233")
                .iconType("square")
                .build();

        Set<ConstraintViolation<CreateUserDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("The username must be between 3 and 30 characters long.",
                violations.iterator().next().getMessage());
    }

    @Test
    void whenUsernameHasInvalidSpaces_thenViolation() {
        CreateUserDTO dto1 = CreateUserDTO.builder()
                .id("1")
                .username(" LeadingSpace")
                .image("img.png")
                .iconColor("#FFFFFF")
                .borderColor("#000000")
                .centerColor("#112233")
                .iconType("square")
                .build();

        Set<ConstraintViolation<CreateUserDTO>> v1 = validator.validate(dto1);
        assertEquals(1, v1.size());

        CreateUserDTO dto2 = CreateUserDTO.builder()
                .id("1")
                .username("TrailingSpace ")
                .image("img.png")
                .iconColor("#FFFFFF")
                .borderColor("#000000")
                .centerColor("#112233")
                .iconType("square")
                .build();

        Set<ConstraintViolation<CreateUserDTO>> v2 = validator.validate(dto2);
        assertEquals(1, v2.size());

        CreateUserDTO dto3 = CreateUserDTO.builder()
                .id("1")
                .username("Bad  Space")
                .image("img.png")
                .iconColor("#FFFFFF")
                .borderColor("#000000")
                .centerColor("#112233")
                .iconType("square")
                .build();

        Set<ConstraintViolation<CreateUserDTO>> v3 = validator.validate(dto3);
        assertEquals(1, v3.size());
    }

    @Test
    void whenColorInvalidFormat_thenViolations() {
        CreateUserDTO dto = CreateUserDTO.builder()
                .id("1")
                .username("User123")
                .image("img.png")
                .iconColor("123456")
                .borderColor("#GGGGGG")
                .centerColor("#1234")
                .iconType("triangle")
                .build();

        Set<ConstraintViolation<CreateUserDTO>> violations = validator.validate(dto);
        assertEquals(3, violations.size());
    }

    @Test
    void whenIconTypeNull_thenViolation() {
        CreateUserDTO dto = CreateUserDTO.builder()
                .id("1")
                .username("UserType")
                .image("img.png")
                .iconColor("#ABCDEF")
                .borderColor("#123456")
                .centerColor("#654321")
                .iconType(null)
                .build();

        Set<ConstraintViolation<CreateUserDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("The icon type is required.", violations.iterator().next().getMessage());
    }

    @Test
    void testEqualsAndHashCode() {
        CreateUserDTO dto1 = CreateUserDTO.builder()
                .id("1").username("User").image("img.png")
                .iconColor("#FFFFFF").borderColor("#000000").centerColor("#112233")
                .iconType("square").build();
        CreateUserDTO dto2 = CreateUserDTO.builder()
                .id("1").username("User").image("img.png")
                .iconColor("#FFFFFF").borderColor("#000000").centerColor("#112233")
                .iconType("square").build();

        // reflexive
        assertEquals(dto1, dto1);
        // symmetric
        assertEquals(dto1, dto2);
        assertEquals(dto2, dto1);
        // consistent hashCode
        assertEquals(dto1.hashCode(), dto2.hashCode());

        // inequality
        CreateUserDTO dto3 = CreateUserDTO.builder().id("2").username("User")
                .image("img.png").iconColor("#FFFFFF").borderColor("#000000")
                .centerColor("#112233").iconType("square").build();
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1.hashCode(), dto3.hashCode());

        // null and different class
        assertNotEquals(dto1, null);
        assertNotEquals(dto1, "some string");
    }

    @Test
    void testEqualsAndHashCodeFullCoverage() {
        // Create three equivalent instances
        CreateUserDTO dto1 = CreateUserDTO.builder()
                .id("A").username("User").image("img.png")
                .iconColor("#111111").borderColor("#222222").centerColor("#333333")
                .iconType("circle").build();
        CreateUserDTO dto2 = CreateUserDTO.builder()
                .id("A").username("User").image("img.png")
                .iconColor("#111111").borderColor("#222222").centerColor("#333333")
                .iconType("circle").build();
        CreateUserDTO dto3 = CreateUserDTO.builder()
                .id("A").username("User").image("img.png")
                .iconColor("#111111").borderColor("#222222").centerColor("#333333")
                .iconType("circle").build();

        // Reflexive
        assertEquals(dto1, dto1);
        // Symmetric
        assertEquals(dto1.equals(dto2), dto2.equals(dto1));
        assertEquals(dto2, dto1);
        // Transitive
        if (dto1.equals(dto2) && dto2.equals(dto3)) {

            assertEquals(dto1, dto3);
        }
        // Consistent
        assertEquals(dto1, dto2);
        assertEquals(dto1, dto2);
        // HashCode consistency
        int hash1 = dto1.hashCode();
        assertEquals(hash1, dto1.hashCode());
        // Equal objects => equal hash codes
        assertEquals(dto1.hashCode(), dto2.hashCode());

        // Inequality with different field values
        CreateUserDTO diff = CreateUserDTO.builder()
                .id("B").username("User").image("img.png")
                .iconColor("#111111").borderColor("#222222").centerColor("#333333")
                .iconType("circle").build();
        assertNotEquals(dto1, diff);
        assertNotEquals(dto1.hashCode(), diff.hashCode());

        // Null and different class

        assertNotEquals(dto1, null);
        assertNotEquals(dto1, "string");
    }

    @Test
    void testToStringContainsFields() {
        CreateUserDTO dto = CreateUserDTO.builder()
                .id("42").username("Name").image("img.png")
                .iconColor("#ABCDEF").borderColor("#123456").centerColor("#654321")
                .iconType("triangle").build();

        String toStr = dto.toString();
        assertTrue(toStr.contains("id=42"));
        assertTrue(toStr.contains("username=Name"));
        assertTrue(toStr.contains("iconColor=#ABCDEF"));
        assertTrue(toStr.contains("iconType=triangle"));
    }

    @Test
    void testCreateUserDTOBuilderToString() {
        // Configurar valores en el builder
        CreateUserDTO.CreateUserDTOBuilder builder = CreateUserDTO.builder()
                .id("user123")
                .username("testUser")
                .image("profile.jpg")
                .iconColor("#FFA500")
                .borderColor("#00FF00")
                .centerColor("#0000FF")
                .iconType("shield");

        // Obtener representación String del builder
        String builderString = builder.toString();

        // Verificar que los campos configurados están presentes
        assertAll(
                () -> assertTrue(builderString.contains("id=user123"),
                        "Debe mostrar el ID configurado"),
                () -> assertTrue(builderString.contains("username=testUser"),
                        "Debe contener el username"),
                () -> assertTrue(builderString.contains("image=profile.jpg"),
                        "Debe mostrar la imagen"),
                () -> assertTrue(builderString.contains("iconColor=#FFA500"),
                        "Debe incluir el color del icono"),
                () -> assertTrue(builderString.contains("borderColor=#00FF00"),
                        "Debe mostrar el color del borde"),
                () -> assertTrue(builderString.contains("centerColor=#0000FF"),
                        "Debe contener el color central"),
                () -> assertTrue(builderString.contains("iconType=shield"),
                        "Debe mostrar el tipo de icono"));
    }
}