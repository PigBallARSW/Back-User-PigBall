package co.edu.eci.pigball.TestDTO;
import org.junit.jupiter.api.Test;

import co.edu.eci.pigball.user.dto.UserResponseDTO;

import static org.junit.jupiter.api.Assertions.*;


class UserResponseDTOTest {

    @Test
    void testNoArgsConstructor() {
        // Act
        UserResponseDTO dto = new UserResponseDTO();

        // Assert
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getUsername());
        assertEquals(0, dto.getGamesPlayed());
        assertEquals(0, dto.getLostGames());
        assertEquals(0, dto.getGamesWon());
        assertEquals(0, dto.getTotalScore());
        assertEquals(0.0, dto.getWinningPercentage());
        assertEquals(0, dto.getBestScore());
        assertNull(dto.getImage());
        assertNull(dto.getBorderColor());
        assertNull(dto.getCenterColor());
        assertNull(dto.getIconColor());
        assertNull(dto.getIconType());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String id = "user123";
        String username = "testUser";
        int gamesPlayed = 10;
        int lostGames = 4;
        int gamesWon = 6;
        int totalScore = 1500;
        double winningPercentage = 60.0;
        int bestScore = 300;
        String image = "profile.jpg";
        String borderColor = "#FF0000";
        String centerColor = "#00FF00";
        String iconColor = "#0000FF";
        String iconType = "star";

        // Act
        UserResponseDTO dto = new UserResponseDTO(
            id, username, gamesPlayed, lostGames, gamesWon, 
            totalScore, winningPercentage, bestScore, image, 
            borderColor, centerColor, iconColor, iconType
        );

        // Assert
        assertAll(
            () -> assertEquals(id, dto.getId()),
            () -> assertEquals(username, dto.getUsername()),
            () -> assertEquals(gamesPlayed, dto.getGamesPlayed()),
            () -> assertEquals(lostGames, dto.getLostGames()),
            () -> assertEquals(gamesWon, dto.getGamesWon()),
            () -> assertEquals(totalScore, dto.getTotalScore()),
            () -> assertEquals(winningPercentage, dto.getWinningPercentage()),
            () -> assertEquals(bestScore, dto.getBestScore()),
            () -> assertEquals(image, dto.getImage()),
            () -> assertEquals(borderColor, dto.getBorderColor()),
            () -> assertEquals(centerColor, dto.getCenterColor()),
            () -> assertEquals(iconColor, dto.getIconColor()),
            () -> assertEquals(iconType, dto.getIconType())
        );
    }

    @Test
    void testBuilder() {
        // Arrange
        String id = "user456";
        String username = "builderUser";
        int gamesPlayed = 20;
        int lostGames = 8;
        int gamesWon = 12;
        int totalScore = 2500;
        double winningPercentage = 60.0;
        int bestScore = 500;
        String image = "builder.jpg";
        String borderColor = "#FFFF00";
        String centerColor = "#FF00FF";
        String iconColor = "#00FFFF";
        String iconType = "trophy";

        // Act
        UserResponseDTO dto = UserResponseDTO.builder()
            .id(id)
            .username(username)
            .gamesPlayed(gamesPlayed)
            .lostGames(lostGames)
            .gamesWon(gamesWon)
            .totalScore(totalScore)
            .winningPercentage(winningPercentage)
            .bestScore(bestScore)
            .image(image)
            .borderColor(borderColor)
            .centerColor(centerColor)
            .iconColor(iconColor)
            .iconType(iconType)
            .build();

        // Assert
        assertAll(
            () -> assertEquals(id, dto.getId()),
            () -> assertEquals(username, dto.getUsername()),
            () -> assertEquals(gamesPlayed, dto.getGamesPlayed()),
            () -> assertEquals(lostGames, dto.getLostGames()),
            () -> assertEquals(gamesWon, dto.getGamesWon()),
            () -> assertEquals(totalScore, dto.getTotalScore()),
            () -> assertEquals(winningPercentage, dto.getWinningPercentage()),
            () -> assertEquals(bestScore, dto.getBestScore()),
            () -> assertEquals(image, dto.getImage()),
            () -> assertEquals(borderColor, dto.getBorderColor()),
            () -> assertEquals(centerColor, dto.getCenterColor()),
            () -> assertEquals(iconColor, dto.getIconColor()),
            () -> assertEquals(iconType, dto.getIconType())
        );
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        UserResponseDTO dto = new UserResponseDTO();
        String id = "user789";
        String username = "setterUser";
        int gamesPlayed = 15;
        int lostGames = 5;
        int gamesWon = 10;
        int totalScore = 2000;
        double winningPercentage = 66.67;
        int bestScore = 400;
        String image = "setter.jpg";
        String borderColor = "#123456";
        String centerColor = "#654321";
        String iconColor = "#ABCDEF";
        String iconType = "medal";

        // Act
        dto.setId(id);
        dto.setUsername(username);
        dto.setGamesPlayed(gamesPlayed);
        dto.setLostGames(lostGames);
        dto.setGamesWon(gamesWon);
        dto.setTotalScore(totalScore);
        dto.setWinningPercentage(winningPercentage);
        dto.setBestScore(bestScore);
        dto.setImage(image);
        dto.setBorderColor(borderColor);
        dto.setCenterColor(centerColor);
        dto.setIconColor(iconColor);
        dto.setIconType(iconType);

        // Assert
        assertAll(
            () -> assertEquals(id, dto.getId()),
            () -> assertEquals(username, dto.getUsername()),
            () -> assertEquals(gamesPlayed, dto.getGamesPlayed()),
            () -> assertEquals(lostGames, dto.getLostGames()),
            () -> assertEquals(gamesWon, dto.getGamesWon()),
            () -> assertEquals(totalScore, dto.getTotalScore()),
            () -> assertEquals(winningPercentage, dto.getWinningPercentage()),
            () -> assertEquals(bestScore, dto.getBestScore()),
            () -> assertEquals(image, dto.getImage()),
            () -> assertEquals(borderColor, dto.getBorderColor()),
            () -> assertEquals(centerColor, dto.getCenterColor()),
            () -> assertEquals(iconColor, dto.getIconColor()),
            () -> assertEquals(iconType, dto.getIconType())
        );
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        UserResponseDTO dto1 = UserResponseDTO.builder()
            .id("user1")
            .username("userOne")
            .gamesPlayed(10)
            .lostGames(4)
            .gamesWon(6)
            .totalScore(1500)
            .winningPercentage(60.0)
            .bestScore(300)
            .image("img1.jpg")
            .borderColor("#111111")
            .centerColor("#222222")
            .iconColor("#333333")
            .iconType("star")
            .build();

        UserResponseDTO dto2 = UserResponseDTO.builder()
            .id("user1")
            .username("userOne")
            .gamesPlayed(10)
            .lostGames(4)
            .gamesWon(6)
            .totalScore(1500)
            .winningPercentage(60.0)
            .bestScore(300)
            .image("img1.jpg")
            .borderColor("#111111")
            .centerColor("#222222")
            .iconColor("#333333")
            .iconType("star")
            .build();

        UserResponseDTO differentDto = UserResponseDTO.builder()
            .id("user2")
            .username("userTwo")
            .gamesPlayed(20)
            .lostGames(8)
            .gamesWon(12)
            .totalScore(2500)
            .winningPercentage(60.0)
            .bestScore(500)
            .image("img2.jpg")
            .borderColor("#AAAAAA")
            .centerColor("#BBBBBB")
            .iconColor("#CCCCCC")
            .iconType("trophy")
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
        UserResponseDTO dto = UserResponseDTO.builder()
            .id("user123")
            .username("testUser")
            .gamesPlayed(10)
            .lostGames(4)
            .gamesWon(6)
            .totalScore(1500)
            .winningPercentage(60.0)
            .bestScore(300)
            .image("profile.jpg")
            .borderColor("#FF0000")
            .centerColor("#00FF00")
            .iconColor("#0000FF")
            .iconType("star")
            .build();

        // Act
        String result = dto.toString();

        // Assert
        assertAll(
            () -> assertTrue(result.contains("user123")),
            () -> assertTrue(result.contains("testUser")),
            () -> assertTrue(result.contains("10")),
            () -> assertTrue(result.contains("4")),
            () -> assertTrue(result.contains("6")),
            () -> assertTrue(result.contains("1500")),
            () -> assertTrue(result.contains("60.0")),
            () -> assertTrue(result.contains("300")),
            () -> assertTrue(result.contains("profile.jpg")),
            () -> assertTrue(result.contains("#FF0000")),
            () -> assertTrue(result.contains("#00FF00")),
            () -> assertTrue(result.contains("#0000FF")),
            () -> assertTrue(result.contains("star"))
        );
    }
}
