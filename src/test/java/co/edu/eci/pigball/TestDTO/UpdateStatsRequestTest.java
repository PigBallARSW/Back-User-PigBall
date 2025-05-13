package co.edu.eci.pigball.TestDTO;

import org.junit.jupiter.api.Test;

import co.edu.eci.pigball.user.model.request.UpdateStatsRequest;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UpdateStatsRequestTest {

    // Tests para la clase principal UpdateStatsRequest
    @Test
    void testUpdateStatsRequestNoArgsConstructor() {
        // Act
        UpdateStatsRequest request = new UpdateStatsRequest();

        // Assert
        assertNull(request.getStats());
        assertNull(request.getPlayers());
    }

    @Test
    void testUpdateStatsRequestAllArgsConstructor() {
        // Arrange
        List<UpdateStatsRequest.Stat> expectedStats = List.of(
                new UpdateStatsRequest.Stat("first1", "second1"),
                new UpdateStatsRequest.Stat("first2", "second2"));
        List<UpdateStatsRequest.PlayerDTO> expectedPlayers = List.of(
                new UpdateStatsRequest.PlayerDTO("id1", "name1", "session1", 1, 10.5, 20.5),
                new UpdateStatsRequest.PlayerDTO("id2", "name2", "session2", 2, 30.5, 40.5));

        // Act
        UpdateStatsRequest request = new UpdateStatsRequest(expectedStats, expectedPlayers);

        // Assert
        assertAll(
                () -> assertEquals(expectedStats, request.getStats()),
                () -> assertEquals(expectedPlayers, request.getPlayers()));
    }

    @Test
    void testUpdateStatsRequestSettersAndGetters() {
        // Arrange
        UpdateStatsRequest request = new UpdateStatsRequest();
        List<UpdateStatsRequest.Stat> expectedStats = List.of(new UpdateStatsRequest.Stat("a", "b"));
        List<UpdateStatsRequest.PlayerDTO> expectedPlayers = List.of(
                new UpdateStatsRequest.PlayerDTO("id", "name", "session", 1, 0.0, 0.0));

        // Act
        request.setStats(expectedStats);
        request.setPlayers(expectedPlayers);

        // Assert
        assertAll(
                () -> assertEquals(expectedStats, request.getStats()),
                () -> assertEquals(expectedPlayers, request.getPlayers()));
    }

    // @Test
    // void testUpdateStatsRequestEqualsAndHashCode() {
    //     // Crear stats y players idénticos pero diferentes instancias
    //     UpdateStatsRequest.Stat stat1 = new UpdateStatsRequest.Stat("first", "second");
    //     UpdateStatsRequest.Stat stat2 = new UpdateStatsRequest.Stat("first", "second");

    //     UpdateStatsRequest.PlayerDTO player1 = new UpdateStatsRequest.PlayerDTO("id", "name", "session", 1, 0.0, 0.0);
    //     UpdateStatsRequest.PlayerDTO player2 = new UpdateStatsRequest.PlayerDTO("id", "name", "session", 1, 0.0, 0.0);

    //     // Crear requests con contenido idéntico
    //     UpdateStatsRequest request1 = new UpdateStatsRequest(List.of(stat1), List.of(player1));
    //     UpdateStatsRequest request2 = new UpdateStatsRequest(List.of(stat2), List.of(player2));

    //     // Casos diferentes
    //     UpdateStatsRequest emptyRequest = new UpdateStatsRequest(List.of(), List.of());
    //     UpdateStatsRequest differentStats = new UpdateStatsRequest(
    //             List.of(new UpdateStatsRequest.Stat("diff", "values")),
    //             List.of(player1));
    //     UpdateStatsRequest differentPlayers = new UpdateStatsRequest(
    //             List.of(stat1),
    //             List.of(new UpdateStatsRequest.PlayerDTO("id2", "name2", "session2", 2, 1.0, 1.0)));

    //     // Verificar igualdad
    //     assertEquals(request1, request1, "Un objeto debe ser igual a sí mismo");
    //     assertEquals(request1, request2, "Objetos con mismo contenido deben ser iguales");
    //     assertEquals(request2, request1, "La igualdad debe ser simétrica");

    //     // Verificar desigualdad
    //     assertNotEquals(request1, emptyRequest, "Debería ser diferente a request vacío");
    //     assertNotEquals(request1, differentStats, "Debería ser diferente con stats distintos");
    //     assertNotEquals(request1, differentPlayers, "Debería ser diferente con players distintos");
    //     assertNotEquals(request1, null, "No debería ser igual a null");
    //     assertNotEquals(request1, new Object(), "No debería ser igual a otro tipo de objeto");

    //     // Verificar hash codes
    //     assertEquals(request1.hashCode(), request2.hashCode(),
    //             "Hash codes deben ser iguales para objetos iguales");
    // }

    // @Test
    // void testUpdateStatsRequestToString() {
    //     // Arrange
    //     UpdateStatsRequest request = new UpdateStatsRequest(
    //             List.of(new UpdateStatsRequest.Stat("first", "second")),
    //             List.of(new UpdateStatsRequest.PlayerDTO("id", "name", "session", 1, 10.0, 20.0)));

    //     // Act
    //     String result = request.toString();

    //     // Assert
    //     assertAll(
    //             () -> assertTrue(result.contains("stats=")),
    //             () -> assertTrue(result.contains("players=")),
    //             () -> assertTrue(result.contains("first")),
    //             () -> assertTrue(result.contains("second")),
    //             () -> assertTrue(result.contains("id")),
    //             () -> assertTrue(result.contains("name")));
    // }

    // Tests para la clase interna Stat
    @Test
    void testStatNoArgsConstructor() {
        // Act
        UpdateStatsRequest.Stat stat = new UpdateStatsRequest.Stat();

        // Assert
        assertNull(stat.getFirst());
        assertNull(stat.getSecond());
    }

    @Test
    void testStatAllArgsConstructor() {
        // Arrange
        String expectedFirst = "firstValue";
        String expectedSecond = "secondValue";

        // Act
        UpdateStatsRequest.Stat stat = new UpdateStatsRequest.Stat(expectedFirst, expectedSecond);

        // Assert
        assertAll(
                () -> assertEquals(expectedFirst, stat.getFirst()),
                () -> assertEquals(expectedSecond, stat.getSecond()));
    }

    @Test
    void testStatSettersAndGetters() {
        // Arrange
        UpdateStatsRequest.Stat stat = new UpdateStatsRequest.Stat();
        String expectedFirst = "newFirst";
        String expectedSecond = "newSecond";

        // Act
        stat.setFirst(expectedFirst);
        stat.setSecond(expectedSecond);

        // Assert
        assertAll(
                () -> assertEquals(expectedFirst, stat.getFirst()),
                () -> assertEquals(expectedSecond, stat.getSecond()));
    }

    @Test
    void testStatEqualsAndHashCode() {
        // Arrange
        UpdateStatsRequest.Stat stat1 = new UpdateStatsRequest.Stat("first", "second");
        UpdateStatsRequest.Stat stat2 = new UpdateStatsRequest.Stat("first", "second");
        UpdateStatsRequest.Stat differentStat = new UpdateStatsRequest.Stat("different", "values");

        // Assert
        assertAll(
                () -> assertEquals(stat1, stat2),
                () -> assertNotEquals(stat1, differentStat),
                () -> assertEquals(stat1.hashCode(), stat2.hashCode()),
                () -> assertNotEquals(stat1.hashCode(), differentStat.hashCode()));
    }

    @Test
    void testStatToString() {
        // Arrange
        UpdateStatsRequest.Stat stat = new UpdateStatsRequest.Stat("firstVal", "secondVal");

        // Act
        String result = stat.toString();

        // Assert
        assertAll(
                () -> assertTrue(result.contains("firstVal")),
                () -> assertTrue(result.contains("secondVal")));
    }

    // Tests para la clase interna PlayerDTO
    @Test
    void testPlayerDTONoArgsConstructor() {
        // Act
        UpdateStatsRequest.PlayerDTO player = new UpdateStatsRequest.PlayerDTO();

        // Assert
        assertNull(player.getId());
        assertNull(player.getName());
        assertNull(player.getSessionId());
        assertEquals(0, player.getTeam());
        assertEquals(0.0, player.getX());
        assertEquals(0.0, player.getY());
    }

    @Test
    void testPlayerDTOAllArgsConstructor() {
        // Arrange
        String expectedId = "player123";
        String expectedName = "Test Player";
        String expectedSessionId = "session456";
        int expectedTeam = 1;
        double expectedX = 10.5;
        double expectedY = 20.5;

        // Act
        UpdateStatsRequest.PlayerDTO player = new UpdateStatsRequest.PlayerDTO(
                expectedId, expectedName, expectedSessionId, expectedTeam, expectedX, expectedY);

        // Assert
        assertAll(
                () -> assertEquals(expectedId, player.getId()),
                () -> assertEquals(expectedName, player.getName()),
                () -> assertEquals(expectedSessionId, player.getSessionId()),
                () -> assertEquals(expectedTeam, player.getTeam()),
                () -> assertEquals(expectedX, player.getX()),
                () -> assertEquals(expectedY, player.getY()));
    }

    @Test
    void testPlayerDTOSettersAndGetters() {
        // Arrange
        UpdateStatsRequest.PlayerDTO player = new UpdateStatsRequest.PlayerDTO();
        String expectedId = "newPlayer";
        String expectedName = "New Name";
        String expectedSessionId = "newSession";
        int expectedTeam = 2;
        double expectedX = 30.5;
        double expectedY = 40.5;

        // Act
        player.setId(expectedId);
        player.setName(expectedName);
        player.setSessionId(expectedSessionId);
        player.setTeam(expectedTeam);
        player.setX(expectedX);
        player.setY(expectedY);

        // Assert
        assertAll(
                () -> assertEquals(expectedId, player.getId()),
                () -> assertEquals(expectedName, player.getName()),
                () -> assertEquals(expectedSessionId, player.getSessionId()),
                () -> assertEquals(expectedTeam, player.getTeam()),
                () -> assertEquals(expectedX, player.getX()),
                () -> assertEquals(expectedY, player.getY()));
    }

    @Test
    void testPlayerDTOEqualsAndHashCode() {
        // Arrange
        UpdateStatsRequest.PlayerDTO player1 = new UpdateStatsRequest.PlayerDTO(
                "id1", "name1", "session1", 1, 10.0, 20.0);
        UpdateStatsRequest.PlayerDTO player2 = new UpdateStatsRequest.PlayerDTO(
                "id1", "name1", "session1", 1, 10.0, 20.0);
        UpdateStatsRequest.PlayerDTO differentPlayer = new UpdateStatsRequest.PlayerDTO(
                "id2", "name2", "session2", 2, 30.0, 40.0);

        // Assert
        assertAll(
                () -> assertEquals(player1, player2),
                () -> assertNotEquals(player1, differentPlayer),
                () -> assertEquals(player1.hashCode(), player2.hashCode()),
                () -> assertNotEquals(player1.hashCode(), differentPlayer.hashCode()));
    }

    @Test
    void testPlayerDTOToString() {
        // Arrange
        UpdateStatsRequest.PlayerDTO player = new UpdateStatsRequest.PlayerDTO(
                "player123", "Test Player", "session456", 1, 10.5, 20.5);

        // Act
        String result = player.toString();

        // Assert
        assertAll(
                () -> assertTrue(result.contains("player123")),
                () -> assertTrue(result.contains("Test Player")),
                () -> assertTrue(result.contains("session456")),
                () -> assertTrue(result.contains("1")),
                () -> assertTrue(result.contains("10.5")),
                () -> assertTrue(result.contains("20.5")));
    }
}
