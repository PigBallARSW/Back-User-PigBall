package co.edu.eci.pigball.TestModel;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.eci.pigball.user.model.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .id("1")
                .username("jag")
                .lostGames(2)
                .gamesWon(3)
                .totalScore(100)
                .bestScore(50)
                .friendsIds(new HashSet<>(Arrays.asList("2", "3")))
                .build();
    }

    @Test
    public void testIncrementLostGames() {
        user.incrementLostGames();
        assertEquals(3, user.getLostGames());
    }

    @Test
    public void testIncrementGamesWon() {
        user.incrementGamesWon();
        assertEquals(4, user.getGamesWon());
    }

    @Test
    public void testAddToTotalScoreWithPositivePoints() {
        user.addToTotalScore(20);
        assertEquals(120, user.getTotalScore());
    }

    @Test
    public void testAddToTotalScoreWithNegativePoints() {
        user.addToTotalScore(-10);
        assertEquals(100, user.getTotalScore()); // No cambia
    }

    @Test
    public void testUpdateBestScoreWithHigherScore() {
        user.updateBestScore(60);
        assertEquals(60, user.getBestScore());
    }

    @Test
    public void testUpdateBestScoreWithLowerScore() {
        user.updateBestScore(40);
        assertEquals(50, user.getBestScore()); // Permanece igual
    }

    @Test
    public void testGetGamesPlayed() {
        assertEquals(5, user.getGamesPlayed()); // 2 lost + 3 won
    }

    @Test
    public void testGetWinningPercentageWithGamesPlayed() {
        assertEquals(60.0, user.getWinningPercentage(), 0.001); // (3/5)*100 = 60%
    }

    @Test
    public void testGetWinningPercentageWithNoGames() {
        User newUser = User.builder().username("new").build();
        assertEquals(0.0, newUser.getWinningPercentage(), 0.001);
    }


    @Test
    public void testBuilderWithDefaults() {
        User defaultUser = User.builder().username("default").build();
        assertEquals(0, defaultUser.getLostGames());
        assertEquals(0, defaultUser.getTotalScore());
        assertTrue(defaultUser.getFriendsIds().isEmpty());
    }

    @Test
    public void testAddNullFriendId() {
        user.addFriendId(null);
        assertEquals(2, user.getFriendsIds().size()); // No cambios
    }

    @Test
    public void testAddFriendId() {
        user.addFriendId("4");
        assertTrue(user.getFriendsIds().contains("4")); // ✅ Ahora es mutable
    }

    @Test
    public void testRemoveFriendId() {
        user.removeFriendId("2");
        assertFalse(user.getFriendsIds().contains("2")); // ✅
    }

    @Test
    public void testRemoveNonExistentFriendId() {
        user.removeFriendId("99");
        assertEquals(2, user.getFriendsIds().size()); // ✅
}

   @Test
    void testHashCodeConsistency() {
        // Arrange
        User user1 = User.builder()
                .id("user123")
                .username("testUser")
                .build();

        User user2 = User.builder()
                .id("user123")
                .username("testUser")
                .build();

        // Act & Assert
        assertEquals(user1.hashCode(), user2.hashCode());
        assertEquals(user1.hashCode(), user1.hashCode()); // Consistencia en múltiples llamadas
    }

    @Test
    void testHashCodeDifference() {
        // Arrange
        User user1 = User.builder()
                .id("user123")
                .username("testUser")
                .build();

        User user2 = User.builder()
                .id("user456")
                .username("differentUser")
                .build();

        // Act & Assert
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testEqualsWithSameObject() {
        // Arrange
        User user = User.builder()
                .id("user123")
                .username("testUser")
                .build();

        // Act & Assert
        assertEquals(user, user);
    }

    @Test
    void testEqualsWithEqualObjects() {
        // Arrange
        User user1 = User.builder()
                .id("user123")
                .username("testUser")
                .gamesWon(5)
                .lostGames(2)
                .build();

        User user2 = User.builder()
                .id("user123")
                .username("testUser")
                .gamesWon(5)
                .lostGames(2)
                .build();

        // Act & Assert
        assertEquals(user1, user2);
    }

    @Test
    void testEqualsWithDifferentObjects() {
        // Arrange
        User user1 = User.builder()
                .id("user123")
                .username("testUser")
                .build();

        User user2 = User.builder()
                .id("user456")
                .username("differentUser")
                .build();

        // Act & Assert
        assertNotEquals(user1, user2);
    }

    @Test
    void testEqualsWithNull() {
        // Arrange
        User user = User.builder()
                .id("user123")
                .username("testUser")
                .build();

        // Act & Assert
        assertNotEquals(null, user);
    }

    @Test
    void testEqualsWithDifferentClass() {
        // Arrange
        User user = User.builder()
                .id("user123")
                .username("testUser")
                .build();

        Object otherObject = new Object();

        // Act & Assert
        assertNotEquals(user, otherObject);
    }

    @Test
    void testToStringContainsRelevantInformation() {
        // Arrange
        User user = User.builder()
                .id("user123")
                .username("testUser")
                .gamesWon(3)
                .lostGames(1)
                .totalScore(100)
                .bestScore(50)
                .image("profile.jpg")
                .iconType("star")
                .borderColor("#FF0000")
                .centerColor("#00FF00")
                .iconColor("#0000FF")
                .friendsIds(Set.of("friend1", "friend2"))
                .build();

        // Act
        String result = user.toString();

        // Assert
        assertAll(
                () -> assertTrue(result.contains("user123")),
                () -> assertTrue(result.contains("testUser")),
                () -> assertTrue(result.contains("3")),
                () -> assertTrue(result.contains("1")),
                () -> assertTrue(result.contains("100")),
                () -> assertTrue(result.contains("50")),
                () -> assertTrue(result.contains("profile.jpg")),
                () -> assertTrue(result.contains("star")),
                () -> assertTrue(result.contains("#FF0000")),
                () -> assertTrue(result.contains("#00FF00")),
                () -> assertTrue(result.contains("#0000FF")),
                () -> assertTrue(result.contains("friendsIds"))
        );
    }

    @Test
    void testSetters() {
        // Arrange
        User user = new User();
        String expectedId = "newId123";
        int expectedLostGames = 3;
        int expectedGamesWon = 5;
        int expectedTotalScore = 150;
        int expectedBestScore = 75;
        String expectedIconType = "medal";
        String expectedCenterColor = "#123456";
        String expectedIconColor = "#654321";
        Set<String> expectedFriendsIds = Set.of("friend1", "friend2");

        // Act
        user.setId(expectedId);
        user.setLostGames(expectedLostGames);
        user.setGamesWon(expectedGamesWon);
        user.setTotalScore(expectedTotalScore);
        user.setBestScore(expectedBestScore);
        user.setIconType(expectedIconType);
        user.setCenterColor(expectedCenterColor);
        user.setIconColor(expectedIconColor);
        user.setFriendsIds(expectedFriendsIds);

        // Assert
        assertAll(
                () -> assertEquals(expectedId, user.getId()),
                () -> assertEquals(expectedLostGames, user.getLostGames()),
                () -> assertEquals(expectedGamesWon, user.getGamesWon()),
                () -> assertEquals(expectedTotalScore, user.getTotalScore()),
                () -> assertEquals(expectedBestScore, user.getBestScore()),
                () -> assertEquals(expectedIconType, user.getIconType()),
                () -> assertEquals(expectedCenterColor, user.getCenterColor()),
                () -> assertEquals(expectedIconColor, user.getIconColor()),
                () -> assertEquals(expectedFriendsIds, user.getFriendsIds())
        );
    }
}
