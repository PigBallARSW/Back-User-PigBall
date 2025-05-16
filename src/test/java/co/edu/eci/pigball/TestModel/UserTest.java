package co.edu.eci.pigball.TestModel;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.eci.pigball.user.model.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
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
    void testIncrementLostGames() {
        user.incrementLostGames();
        assertEquals(3, user.getLostGames());
    }

    @Test
    void testIncrementGamesWon() {
        user.incrementGamesWon();
        assertEquals(4, user.getGamesWon());
    }

    @Test
    void testAddToTotalScoreWithPositivePoints() {
        user.addToTotalScore(20);
        assertEquals(120, user.getTotalScore());
    }

    @Test
    void testAddToTotalScoreWithNegativePoints() {
        user.addToTotalScore(-10);
        assertEquals(100, user.getTotalScore()); // No cambia
    }

    @Test
    void testUpdateBestScoreWithHigherScore() {
        user.updateBestScore(60);
        assertEquals(60, user.getBestScore());
    }

    @Test
    void testUpdateBestScoreWithLowerScore() {
        user.updateBestScore(40);
        assertEquals(50, user.getBestScore()); // Permanece igual
    }

    @Test
    void testGetGamesPlayed() {
        assertEquals(5, user.getGamesPlayed()); // 2 lost + 3 won
    }

    @Test
    void testGetWinningPercentageWithGamesPlayed() {
        assertEquals(60.0, user.getWinningPercentage(), 0.001); // (3/5)*100 = 60%
    }

    @Test
    void testGetWinningPercentageWithNoGames() {
        User newUser = User.builder().username("new").build();
        assertEquals(0.0, newUser.getWinningPercentage(), 0.001);
    }

    @Test
    void testBuilderWithDefaults() {
        User defaultUser = User.builder().username("default").build();
        assertEquals(0, defaultUser.getLostGames());
        assertEquals(0, defaultUser.getTotalScore());
        assertTrue(defaultUser.getFriendsIds().isEmpty());
    }

    @Test
    void testAddNullFriendId() {
        user.addFriendId(null);
        assertEquals(2, user.getFriendsIds().size()); // No cambios
    }

    @Test
    void testAddFriendId() {
        user.addFriendId("4");
        assertTrue(user.getFriendsIds().contains("4")); // ✅ Ahora es mutable
    }

    @Test
    void testRemoveFriendId() {
        user.removeFriendId("2");
        assertFalse(user.getFriendsIds().contains("2")); // ✅
    }

    @Test
    void testRemoveNonExistentFriendId() {
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
        User user1 = User.builder()
                .id("user123")
                .username("testUser")
                .build();

        // Act & Assert
        assertEquals(user1, user1);
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
        User user1 = User.builder()
                .id("user123")
                .username("testUser")
                .build();

        // Act & Assert
        assertNotEquals(null, user1);
    }

    @Test
    void testEqualsWithDifferentClass() {
        // Arrange
        User user1 = User.builder()
                .id("user123")
                .username("testUser")
                .build();

        Object otherObject = new Object();

        // Act & Assert
        assertNotEquals(user1, otherObject);
    }

    @Test
    void testToStringContainsRelevantInformation() {
        // Arrange
        User user1 = User.builder()
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
        String result = user1.toString();

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
                () -> assertTrue(result.contains("friendsIds")));
    }

    @Test
    void testSetters() {
        // Arrange
        User user1 = new User();
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
        user1.setId(expectedId);
        user1.setLostGames(expectedLostGames);
        user1.setGamesWon(expectedGamesWon);
        user1.setTotalScore(expectedTotalScore);
        user1.setBestScore(expectedBestScore);
        user1.setIconType(expectedIconType);
        user1.setCenterColor(expectedCenterColor);
        user1.setIconColor(expectedIconColor);
        user1.setFriendsIds(expectedFriendsIds);

        // Assert
        assertAll(
                () -> assertEquals(expectedId, user1.getId()),
                () -> assertEquals(expectedLostGames, user1.getLostGames()),
                () -> assertEquals(expectedGamesWon, user1.getGamesWon()),
                () -> assertEquals(expectedTotalScore, user1.getTotalScore()),
                () -> assertEquals(expectedBestScore, user1.getBestScore()),
                () -> assertEquals(expectedIconType, user1.getIconType()),
                () -> assertEquals(expectedCenterColor, user1.getCenterColor()),
                () -> assertEquals(expectedIconColor, user1.getIconColor()),
                () -> assertEquals(expectedFriendsIds, user1.getFriendsIds()));
    }

    void testAddExistingFriendId() {
        user.addFriendId("2"); // Ya existe en el setup
        assertEquals(2, user.getFriendsIds().size()); // Tamaño no cambia
    }

    // Pruebas para múltiples incrementos
    @Test
    void testMultipleIncrementLostGames() {
        user.incrementLostGames();
        user.incrementLostGames();
        assertEquals(4, user.getLostGames());
    }

    // Actualizar bestScore con mismo valor
    @Test
    void testUpdateBestScoreWithSameScore() {
        user.updateBestScore(50); // Mismo que el valor inicial
        assertEquals(50, user.getBestScore());
    }

    // Prueba addToTotalScore con 0 puntos
    @Test
    void testAddZeroToTotalScore() {
        user.addToTotalScore(0);
        assertEquals(100, user.getTotalScore());
    }

    // Prueba getWinningPercentage con 0 juegos ganados
    @Test
    void testGetWinningPercentageWithZeroWins() {
        User newUser = User.builder()
                .lostGames(5)
                .gamesWon(0)
                .build();
        assertEquals(0.0, newUser.getWinningPercentage(), 0.001);
    }

    // Prueba constructor sin builder
    @Test
    void testNoArgsConstructor() {
        User emptyUser = new User();
        assertNotNull(emptyUser); // Asegura que se puede crear
    }

    // Prueba campos de colores en equals
    @Test
    void testEqualsWithDifferentBorderColor() {
        User user1 = User.builder().id("1").borderColor("#000").build();
        User user2 = User.builder().id("1").borderColor("#FFF").build();
        assertNotEquals(user1, user2);
    }

    // Prueba para cubrir el branch faltante en removeFriendId (friendId = null):
    @Test
    void testRemoveNullFriendId() {
        user.removeFriendId(null);
        assertEquals(2, user.getFriendsIds().size()); // No debe modificar el set
        assertFalse(user.getFriendsIds().contains(null)); // Asegurar que no guarda nulls
    }

    // Mejorar cobertura de hashCode() y equals():
    @Test
    void testEqualsWithAllFieldsDifference() {
        User user1 = User.builder()
                .id("1")
                .username("user1")
                .lostGames(2)
                .gamesWon(3)
                .totalScore(100)
                .bestScore(50)
                .image("img1.jpg")
                .iconType("star")
                .borderColor("#000")
                .centerColor("#FFF")
                .iconColor("#CCC")
                .friendsIds(Set.of("2"))
                .build();

        User user2 = User.builder()
                .id("2") // Diferente ID
                .username("user2")
                .lostGames(5)
                .gamesWon(1)
                .totalScore(200)
                .bestScore(100)
                .image("img2.jpg")
                .iconType("circle")
                .borderColor("#FFF")
                .centerColor("#000")
                .iconColor("#999")
                .friendsIds(Set.of("3"))
                .build();

        assertNotEquals(user1, user2);
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testEqualsWithOnlyIdDifference() {
        User user1 = User.builder().id("1").build();
        User user2 = User.builder().id("2").build();
        assertNotEquals(user1, user2);
    }

    @Test
    void testHashCodeWithDifferentIconType() {
        User user1 = User.builder().id("1").iconType("star").build();
        User user2 = User.builder().id("1").iconType("circle").build();
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }

    // Pruebas para UserBuilder
    @Test
    void testBuilderToString() {
        User.UserBuilder builder = User.builder()
                .id("123")
                .username("builderTest")
                .image("builder.jpg");

        String builderStr = builder.toString();
        assertTrue(builderStr.contains("id=123"));
        assertTrue(builderStr.contains("username=builderTest"));
        assertTrue(builderStr.contains("image=builder.jpg"));
    }

    @Test
    void testBuilderWithAllFields() {
        User user1 = User.builder()
                .id("full")
                .username("fullUser")
                .lostGames(1)
                .gamesWon(2)
                .totalScore(30)
                .bestScore(20)
                .image("full.jpg")
                .iconType("fullIcon")
                .borderColor("#111")
                .centerColor("#222")
                .iconColor("#333")
                .friendsIds(Set.of("friend1"))
                .build();

        assertNotNull(user1);
        assertEquals("full", user1.getId());
        assertEquals("#111", user1.getBorderColor());
        assertTrue(user1.getFriendsIds().contains("friend1"));
    }

    // pruebas para toString
    @Test
    void testToStringWithEmptyFields() {
        User user1 = new User();
        user1.setId("empty");
        String str = user1.toString();

        System.out.println(str);
        assertTrue(str.contains("id=empty"));
        assertTrue(str.contains("lostGames=0")); // Valores por defecto
        assertTrue(str.contains("friendsIds=[]")); // Set vacío
    }

    @Test
    void testToStringWithSpecialCharacters() {
        User user1 = User.builder()
                .borderColor("#!@#")
                .username("user@name")
                .iconType("type/with/slashes")
                .build();

        String str = user1.toString();
        assertTrue(str.contains("#!@#"));
        assertTrue(str.contains("user@name"));
        assertTrue(str.contains("type/with/slashes"));
    }

    // prueba para toEquals
    @Test
    void testEqualsWithDifferentImage() {
        User user1 = User.builder().id("1").image("img1").build();
        User user2 = User.builder().id("1").image("img2").build();
        assertNotEquals(user1, user2);
    }

    @Test
    void testEqualsWithDifferentFriendsIds() {
        User user1 = User.builder().id("1").friendsIds(Set.of("a")).build();
        User user2 = User.builder().id("1").friendsIds(Set.of("b")).build();
        assertNotEquals(user1, user2);
    }

    // edge case hashcode
    @Test
    void testHashCodeConsistencyAfterModification() {
        User user1 = User.builder().id("1").build();
        int initialHash = user1.hashCode();

        user.setUsername("newName");
        assertNotEquals(initialHash, user.hashCode());
    }

    @Test
    void testEqualsWithDifferentImage2() {
        User user1 = User.builder().id("1").image("img1.jpg").build();
        User user2 = User.builder().id("1").image("img2.jpg").build();
        assertNotEquals(user1, user2, "Deben ser diferentes por la imagen");
    }

    @Test
    void testEqualsWithNullImageVsNonNull() {
        User user1 = User.builder().id("1").image(null).build();
        User user2 = User.builder().id("1").image("img.jpg").build();
        assertNotEquals(user1, user2, "Null vs no-null en imagen debe ser diferente");
    }

    @Test
    void testEqualsWithDifferentIconType() {
        User user1 = User.builder().id("1").iconType("star").build();
        User user2 = User.builder().id("1").iconType("circle").build();
        assertNotEquals(user1, user2, "Deben diferir por iconType");
    }

    @Test
    void testEqualsWithDifferentBorderColor3() {
        User user1 = User.builder().id("1").borderColor("#000").build();
        User user2 = User.builder().id("1").borderColor("#FFF").build();
        assertNotEquals(user1, user2, "borderColor diferente debe hacerlos distintos");
    }

    @Test
    void testEqualsWithDifferentCenterColor() {
        User user1 = User.builder().id("1").centerColor("#111").build();
        User user2 = User.builder().id("1").centerColor("#222").build();
        assertNotEquals(user1, user2);
    }

    @Test
    void testEqualsWithDifferentIconColor() {
        User user1 = User.builder().id("1").iconColor("red").build();
        User user2 = User.builder().id("1").iconColor("blue").build();
        assertNotEquals(user1, user2);
    }

    @Test
    void testEqualsWithDifferentLostGames() {
        User user1 = User.builder().id("1").lostGames(2).build();
        User user2 = User.builder().id("1").lostGames(5).build();
        assertNotEquals(user1, user2);
    }

    @Test
    void testEqualsWithDifferentTotalScore() {
        User user1 = User.builder().id("1").totalScore(100).build();
        User user2 = User.builder().id("1").totalScore(200).build();
        assertNotEquals(user1, user2);
    }

    @Test
    void testEqualsWithDifferentFriendsIdsContent() {
        User user1 = User.builder().id("1").friendsIds(Set.of("2")).build();
        User user2 = User.builder().id("1").friendsIds(Set.of("3")).build();
        assertNotEquals(user1, user2, "friendsIds diferentes deben hacerlos desiguales");
    }

    @Test
    void testEqualsWithDifferentFriendsIdsOrder() {
        // Los Set no tienen orden, pero se prueba igualación de contenido
        User user1 = User.builder().id("1").friendsIds(Set.of("2", "3")).build();
        User user2 = User.builder().id("1").friendsIds(Set.of("3", "2")).build();
        assertEquals(user1, user2, "El orden en Sets no debe afectar igualdad");
    }

    @Test
    void testEqualsWithNullFriendsIds() {
        User user1 = User.builder().id("1").friendsIds(null).build();
        User user2 = User.builder().id("1").build(); // friendsIds por defecto = empty
        assertNotEquals(user1, user2, "null friendsIds vs empty debe ser diferente");
    }

    @Test
    void testEqualsWithDifferentBestScore() {
        User user1 = User.builder().id("1").bestScore(10).build();
        User user2 = User.builder().id("1").bestScore(20).build();
        assertNotEquals(user1, user2);
    }

    @Test
    void testEqualsWithPartialNulls() {
        User user1 = User.builder().id("1").username(null).build();
        User user2 = User.builder().id("1").username("notNull").build();
        assertNotEquals(user1, user2, "username null vs no-null debe ser diferente");
    }
}
