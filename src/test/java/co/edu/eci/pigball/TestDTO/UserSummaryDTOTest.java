package co.edu.eci.pigball.TestDTO;

import org.junit.jupiter.api.Test;

import co.edu.eci.pigball.user.dto.UserSummaryDTO;

import static org.junit.jupiter.api.Assertions.*;

class UserSummaryDTOTest {

        @Test
        void testBuilderAndGetters() {
                // Arrange
                String expectedId = "user123";
                String expectedUsername = "testUser";
                int expectedGamesWon = 10;
                String expectedImage = "profile.jpg";
                String expectedIconType = "star";
                String expectedBorderColor = "#FF0000";
                String expectedCenterColor = "#00FF00";
                String expectedIconColor = "#0000FF";
                String expectedLostGames = "5";

                // Act
                UserSummaryDTO dto = UserSummaryDTO.builder()
                                .id(expectedId)
                                .username(expectedUsername)
                                .gamesWon(expectedGamesWon)
                                .image(expectedImage)
                                .iconType(expectedIconType)
                                .borderColor(expectedBorderColor)
                                .centerColor(expectedCenterColor)
                                .iconColor(expectedIconColor)
                                .lostGames(expectedLostGames)
                                .build();

                // Assert
                assertAll(
                                () -> assertEquals(expectedId, dto.getId()),
                                () -> assertEquals(expectedUsername, dto.getUsername()),
                                () -> assertEquals(expectedGamesWon, dto.getGamesWon()),
                                () -> assertEquals(expectedImage, dto.getImage()),
                                () -> assertEquals(expectedIconType, dto.getIconType()),
                                () -> assertEquals(expectedBorderColor, dto.getBorderColor()),
                                () -> assertEquals(expectedCenterColor, dto.getCenterColor()),
                                () -> assertEquals(expectedIconColor, dto.getIconColor()),
                                () -> assertEquals(expectedLostGames, dto.getLostGames()));
        }

        @Test
        void testNoArgsConstructor() {
                // Act
                UserSummaryDTO dto = new UserSummaryDTO();

                // Assert
                assertNull(dto.getId());
                assertNull(dto.getUsername());
                assertEquals(0, dto.getGamesWon());
                assertNull(dto.getImage());
                assertNull(dto.getIconType());
                assertNull(dto.getBorderColor());
                assertNull(dto.getCenterColor());
                assertNull(dto.getIconColor());
                assertNull(dto.getLostGames());
        }

        @Test
        void testAllArgsConstructor() {
                // Arrange
                String expectedId = "user456";
                String expectedUsername = "allArgsUser";
                int expectedGamesWon = 15;
                String expectedImage = "allargs.jpg";
                String expectedIconType = "trophy";
                String expectedBorderColor = "#123456";
                String expectedCenterColor = "#654321";
                String expectedIconColor = "#ABCDEF";
                String expectedLostGames = "3";

                // Act
                UserSummaryDTO dto = new UserSummaryDTO(
                                expectedId, expectedUsername, expectedGamesWon,
                                expectedImage, expectedIconType, expectedBorderColor,
                                expectedCenterColor, expectedIconColor, expectedLostGames);

                // Assert
                assertAll(
                                () -> assertEquals(expectedId, dto.getId()),
                                () -> assertEquals(expectedUsername, dto.getUsername()),
                                () -> assertEquals(expectedGamesWon, dto.getGamesWon()),
                                () -> assertEquals(expectedImage, dto.getImage()),
                                () -> assertEquals(expectedIconType, dto.getIconType()),
                                () -> assertEquals(expectedBorderColor, dto.getBorderColor()),
                                () -> assertEquals(expectedCenterColor, dto.getCenterColor()),
                                () -> assertEquals(expectedIconColor, dto.getIconColor()),
                                () -> assertEquals(expectedLostGames, dto.getLostGames()));
        }

        @Test
        void testSettersAndGetters() {
                // Arrange
                UserSummaryDTO dto = new UserSummaryDTO();
                String expectedId = "user789";
                String expectedUsername = "setterUser";
                int expectedGamesWon = 20;
                String expectedImage = "setter.jpg";
                String expectedIconType = "medal";
                String expectedBorderColor = "#111111";
                String expectedCenterColor = "#222222";
                String expectedIconColor = "#333333";
                String expectedLostGames = "7";

                // Act
                dto.setId(expectedId);
                dto.setUsername(expectedUsername);
                dto.setGamesWon(expectedGamesWon);
                dto.setImage(expectedImage);
                dto.setIconType(expectedIconType);
                dto.setBorderColor(expectedBorderColor);
                dto.setCenterColor(expectedCenterColor);
                dto.setIconColor(expectedIconColor);
                dto.setLostGames(expectedLostGames);

                // Assert
                assertAll(
                                () -> assertEquals(expectedId, dto.getId()),
                                () -> assertEquals(expectedUsername, dto.getUsername()),
                                () -> assertEquals(expectedGamesWon, dto.getGamesWon()),
                                () -> assertEquals(expectedImage, dto.getImage()),
                                () -> assertEquals(expectedIconType, dto.getIconType()),
                                () -> assertEquals(expectedBorderColor, dto.getBorderColor()),
                                () -> assertEquals(expectedCenterColor, dto.getCenterColor()),
                                () -> assertEquals(expectedIconColor, dto.getIconColor()),
                                () -> assertEquals(expectedLostGames, dto.getLostGames()));
        }

        @Test
        void testEqualsAndHashCode() {
                // Arrange
                UserSummaryDTO dto1 = UserSummaryDTO.builder()
                                .id("user1")
                                .username("userOne")
                                .gamesWon(10)
                                .image("img1.jpg")
                                .iconType("icon1")
                                .borderColor("#AAAAAA")
                                .centerColor("#BBBBBB")
                                .iconColor("#CCCCCC")
                                .lostGames("4")
                                .build();

                UserSummaryDTO dto2 = UserSummaryDTO.builder()
                                .id("user1")
                                .username("userOne")
                                .gamesWon(10)
                                .image("img1.jpg")
                                .iconType("icon1")
                                .borderColor("#AAAAAA")
                                .centerColor("#BBBBBB")
                                .iconColor("#CCCCCC")
                                .lostGames("4")
                                .build();

                UserSummaryDTO differentDto = UserSummaryDTO.builder()
                                .id("user2")
                                .username("userTwo")
                                .gamesWon(15)
                                .image("img2.jpg")
                                .iconType("icon2")
                                .borderColor("#DDDDDD")
                                .centerColor("#EEEEEE")
                                .iconColor("#FFFFFF")
                                .lostGames("6")
                                .build();

                // Assert
                assertAll(
                                () -> assertEquals(dto1, dto2),
                                () -> assertNotEquals(dto1, differentDto),
                                () -> assertEquals(dto1.hashCode(), dto2.hashCode()),
                                () -> assertNotEquals(dto1.hashCode(), differentDto.hashCode()));
        }

        @Test
        void testToString() {
                // Arrange
                UserSummaryDTO dto = UserSummaryDTO.builder()
                                .id("user123")
                                .username("testUser")
                                .gamesWon(12)
                                .image("test.jpg")
                                .iconType("testIcon")
                                .borderColor("#FF0000")
                                .centerColor("#00FF00")
                                .iconColor("#0000FF")
                                .lostGames("5")
                                .build();

                // Act
                String result = dto.toString();

                // Assert
                assertAll(
                                () -> assertTrue(result.contains("user123")),
                                () -> assertTrue(result.contains("testUser")),
                                () -> assertTrue(result.contains("12")),
                                () -> assertTrue(result.contains("test.jpg")),
                                () -> assertTrue(result.contains("testIcon")),
                                () -> assertTrue(result.contains("#FF0000")),
                                () -> assertTrue(result.contains("#00FF00")),
                                () -> assertTrue(result.contains("#0000FF")),
                                () -> assertTrue(result.contains("5")));
        }

        @Test
        void testEmptyBuilder() {
                // Act
                UserSummaryDTO dto = UserSummaryDTO.builder().build();

                // Assert
                assertAll(
                                () -> assertNull(dto.getId()),
                                () -> assertNull(dto.getUsername()),
                                () -> assertEquals(0, dto.getGamesWon()),
                                () -> assertNull(dto.getImage()),
                                () -> assertNull(dto.getIconType()),
                                () -> assertNull(dto.getBorderColor()),
                                () -> assertNull(dto.getCenterColor()),
                                () -> assertNull(dto.getIconColor()),
                                () -> assertNull(dto.getLostGames()));
        }

        // Pruebas adicionales para equals() y hashCode()
        @Test
        void testEquals_SameInstance_ReturnsTrue() {
                UserSummaryDTO dto = UserSummaryDTO.builder().id("123").build();
                assertEquals(dto, dto);
        }

        @Test
        void testEquals_NullObject_ReturnsFalse() {
                UserSummaryDTO dto = UserSummaryDTO.builder().id("123").build();
                assertNotEquals(null, dto);
        }

        @Test
        void testEquals_DifferentClass_ReturnsFalse() {
                UserSummaryDTO dto = UserSummaryDTO.builder().id("123").build();
                assertNotEquals(dto, "Not a DTO");
        }

        @Test
        void testEquals_AllFieldsNull_ShouldBeEqual() {
                UserSummaryDTO dto1 = new UserSummaryDTO();
                UserSummaryDTO dto2 = new UserSummaryDTO();
                assertEquals(dto1, dto2);
        }

        @Test
        void testHashCode_AllFieldsNull_ConsistentHash() {
                UserSummaryDTO dto = new UserSummaryDTO();
                assertEquals(dto.hashCode(), dto.hashCode());
        }

        // Pruebas por campo individual
        @Test
        void testEquals_DifferentId_NotEqual() {
                UserSummaryDTO dto1 = UserSummaryDTO.builder().id("1").build();
                UserSummaryDTO dto2 = UserSummaryDTO.builder().id("2").build();
                assertNotEquals(dto1, dto2);
        }

        @Test
        void testEquals_DifferentUsername_NotEqual() {
                UserSummaryDTO dto1 = UserSummaryDTO.builder().username("A").build();
                UserSummaryDTO dto2 = UserSummaryDTO.builder().username("B").build();
                assertNotEquals(dto1, dto2);
        }

        @Test
        void testEquals_DifferentGamesWon_NotEqual() {
                UserSummaryDTO dto1 = UserSummaryDTO.builder().gamesWon(5).build();
                UserSummaryDTO dto2 = UserSummaryDTO.builder().gamesWon(10).build();
                assertNotEquals(dto1, dto2);
        }

        @Test
        void testEquals_DifferentImage_NotEqual() {
                UserSummaryDTO dto1 = UserSummaryDTO.builder().image("img1").build();
                UserSummaryDTO dto2 = UserSummaryDTO.builder().image("img2").build();
                assertNotEquals(dto1, dto2);
        }

        @Test
        void testEquals_DifferentIconType_NotEqual() {
                UserSummaryDTO dto1 = UserSummaryDTO.builder().iconType("star").build();
                UserSummaryDTO dto2 = UserSummaryDTO.builder().iconType("circle").build();
                assertNotEquals(dto1, dto2);
        }

        @Test
        void testEquals_DifferentBorderColor_NotEqual() {
                UserSummaryDTO dto1 = UserSummaryDTO.builder().borderColor("#000").build();
                UserSummaryDTO dto2 = UserSummaryDTO.builder().borderColor("#FFF").build();
                assertNotEquals(dto1, dto2);
        }

        @Test
        void testEquals_DifferentCenterColor_NotEqual() {
                UserSummaryDTO dto1 = UserSummaryDTO.builder().centerColor("#111").build();
                UserSummaryDTO dto2 = UserSummaryDTO.builder().centerColor("#222").build();
                assertNotEquals(dto1, dto2);
        }

        @Test
        void testEquals_DifferentIconColor_NotEqual() {
                UserSummaryDTO dto1 = UserSummaryDTO.builder().iconColor("red").build();
                UserSummaryDTO dto2 = UserSummaryDTO.builder().iconColor("blue").build();
                assertNotEquals(dto1, dto2);
        }

        @Test
        void testEquals_DifferentLostGames_NotEqual() {
                UserSummaryDTO dto1 = UserSummaryDTO.builder().lostGames("3").build();
                UserSummaryDTO dto2 = UserSummaryDTO.builder().lostGames("5").build();
                assertNotEquals(dto1, dto2);
        }

        // Pruebas de combinaciones de null
        @Test
        void testEquals_MixedNullAndNonNull_NotEqual() {
                UserSummaryDTO dto1 = UserSummaryDTO.builder().id(null).username("test").build();
                UserSummaryDTO dto2 = UserSummaryDTO.builder().id("123").username("test").build();
                assertNotEquals(dto1, dto2);
        }

        @Test
        void testHashCode_ConsistencyAcrossCalls() {
                UserSummaryDTO dto = UserSummaryDTO.builder()
                                .id("123")
                                .gamesWon(5)
                                .borderColor("#ABC")
                                .build();

                int hash1 = dto.hashCode();
                int hash2 = dto.hashCode();
                assertEquals(hash1, hash2);
        }

        // Pruebas para valores numéricos especiales
        @Test
        void testEquals_ZeroVsNegativeGamesWon_NotEqual() {
                UserSummaryDTO dto1 = UserSummaryDTO.builder().gamesWon(0).build();
                UserSummaryDTO dto2 = UserSummaryDTO.builder().gamesWon(-1).build();
                assertNotEquals(dto1, dto2);
        }

        // Prueba para verificar que todos los campos se usan en equals
        @Test
        void testEquals_OnlyOneFieldDifferent_NotEqual() {
                UserSummaryDTO base = UserSummaryDTO.builder()
                                .id("1")
                                .username("user")
                                .gamesWon(10)
                                .image("img")
                                .iconType("star")
                                .borderColor("#000")
                                .centerColor("#FFF")
                                .iconColor("red")
                                .lostGames("5")
                                .build();

                UserSummaryDTO modified = UserSummaryDTO.builder()
                                .id("1")
                                .username("user")
                                .gamesWon(10)
                                .image("img")
                                .iconType("star")
                                .borderColor("#000")
                                .centerColor("#FFF")
                                .iconColor("blue") // Solo cambia este campo
                                .lostGames("5")
                                .build();

                assertNotEquals(base, modified);
        }

        // 1. Prueba de igualdad consigo mismo
        @Test
        void testEquals_SameInstance_ReturnsTrue2() {
                UserSummaryDTO dto = UserSummaryDTO.builder().id("self").build();
                assertTrue(dto.equals(dto));
        }

        // 2. Comparación con null
        @Test
        void testEquals_NullComparison_ReturnsFalse() {
                UserSummaryDTO dto = UserSummaryDTO.builder().username("notNull").build();
                assertFalse(dto.equals(null));
        }

        // 3. Comparación con otro tipo de objeto
        @Test
        void testEquals_DifferentClass_ReturnsFalse2() {
                UserSummaryDTO dto = UserSummaryDTO.builder().build();
                assertFalse(dto.equals("Soy un String, no un DTO"));
        }

        // 4. Campos null vs no-null (por cada campo)
        @Test
        void testEquals_NullIdVsNonNullId_NotEqual() {
                UserSummaryDTO dto1 = UserSummaryDTO.builder().id(null).build();
                UserSummaryDTO dto2 = UserSummaryDTO.builder().id("123").build();
                assertNotEquals(dto1, dto2);
        }

        @Test
        void testEquals_NullUsernameVsNonNull_NotEqual() {
                UserSummaryDTO dto1 = UserSummaryDTO.builder().username(null).build();
                UserSummaryDTO dto2 = UserSummaryDTO.builder().username("user").build();
                assertNotEquals(dto1, dto2);
        }

        // Repetir el mismo patrón para los demás campos:
        // image, iconType, borderColor, centerColor, iconColor, lostGames

        // 5. Todos los campos null
        @Test
        void testEquals_AllFieldsNull_Equal() {
                UserSummaryDTO dto1 = new UserSummaryDTO();
                UserSummaryDTO dto2 = new UserSummaryDTO();
                assertEquals(dto1, dto2);
        }

        // 6. Campos String vacíos vs null
        @Test
        void testEquals_EmptyStringVsNull_NotEqual() {
                UserSummaryDTO dto1 = UserSummaryDTO.builder().lostGames(null).build();
                UserSummaryDTO dto2 = UserSummaryDTO.builder().lostGames("").build();
                assertNotEquals(dto1, dto2);
        }

        // 7. Verificar TODOS los campos en equals (caso real faltante)
        @Test
        void testEquals_OnlyLostGamesDifferent_NotEqual() {
                UserSummaryDTO base = UserSummaryDTO.builder()
                                .id("1")
                                .lostGames("3")
                                .build();

                UserSummaryDTO different = UserSummaryDTO.builder()
                                .id("1")
                                .lostGames("5") // Único campo diferente
                                .build();

                assertNotEquals(base, different);
        }

        // 8. Caso especial: gamesWon negativo
        @Test
        void testEquals_NegativeGamesWon_NotEqual() {
                UserSummaryDTO dto1 = UserSummaryDTO.builder().gamesWon(-5).build();
                UserSummaryDTO dto2 = UserSummaryDTO.builder().gamesWon(0).build();
                assertNotEquals(dto1, dto2);
        }

        @Test
        void testBuilderToString() {
                // Configurar valores en el builder
                UserSummaryDTO.UserSummaryDTOBuilder builder = UserSummaryDTO.builder()
                                .id("user123")
                                .username("gamerPro")
                                .gamesWon(15)
                                .borderColor("#FF0000")
                                .iconType("diamond");

                // Obtener representación String del builder
                String builderToString = builder.toString();

                // Verificar que los campos configurados están presentes
                assertAll(
                                () -> assertTrue(builderToString.contains("id=user123")),
                                () -> assertTrue(builderToString.contains("username=gamerPro")),
                                () -> assertTrue(builderToString.contains("gamesWon=15")),
                                () -> assertTrue(builderToString.contains("borderColor=#FF0000")),
                                () -> assertTrue(builderToString.contains("iconType=diamond")));
        }
}