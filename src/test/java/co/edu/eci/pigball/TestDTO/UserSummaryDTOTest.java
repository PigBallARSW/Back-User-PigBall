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
}