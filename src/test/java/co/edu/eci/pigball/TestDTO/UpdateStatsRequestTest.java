package co.edu.eci.pigball.TestDTO;

import org.junit.jupiter.api.Test;

import co.edu.eci.pigball.user.model.request.UpdateStatsRequest;
import co.edu.eci.pigball.user.model.request.UpdateStatsRequest.PlayerDTO;

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

        //
        @Test
        void testStatEqualsWithDifferentFirst() {
                UpdateStatsRequest.Stat stat1 = new UpdateStatsRequest.Stat("first1", "same");
                UpdateStatsRequest.Stat stat2 = new UpdateStatsRequest.Stat("first2", "same");
                assertNotEquals(stat1, stat2);
        }

        @Test
        void testStatEqualsWithDifferentSecond() {
                UpdateStatsRequest.Stat stat1 = new UpdateStatsRequest.Stat("same", "second1");
                UpdateStatsRequest.Stat stat2 = new UpdateStatsRequest.Stat("same", "second2");
                assertNotEquals(stat1, stat2);
        }

        @Test
        void testStatEqualsWithNullFirst() {
                UpdateStatsRequest.Stat stat1 = new UpdateStatsRequest.Stat(null, "second");
                UpdateStatsRequest.Stat stat2 = new UpdateStatsRequest.Stat("not-null", "second");
                assertNotEquals(stat1, stat2);
        }

        @Test
        void testStatEqualsWithBothNull() {
                UpdateStatsRequest.Stat stat1 = new UpdateStatsRequest.Stat(null, null);
                UpdateStatsRequest.Stat stat2 = new UpdateStatsRequest.Stat(null, null);
                assertEquals(stat1, stat2);
        }

        @Test
        void testStatEqualsWithDifferentTypes() {
                UpdateStatsRequest.Stat stat = new UpdateStatsRequest.Stat("a", "b");
                assertNotEquals(stat, "not-a-stat");
        }

        @Test
        void testStatHashCodeWithNulls() {
                UpdateStatsRequest.Stat stat = new UpdateStatsRequest.Stat(null, null);
                assertDoesNotThrow(stat::hashCode);
        }

        @Test
        void testPlayerDTOEqualsWithDifferentId() {
                // Objeto base
                UpdateStatsRequest.PlayerDTO basePlayer = new UpdateStatsRequest.PlayerDTO(
                                "id1", "player1", "session1", 1, 10.0, 20.0);

                // Objeto con ID diferente
                UpdateStatsRequest.PlayerDTO differentPlayer = new UpdateStatsRequest.PlayerDTO(
                                "id2", "player1", "session1", 1, 10.0, 20.0);

                assertNotEquals(basePlayer, differentPlayer);
        }

        @Test
        void testPlayerDTOEqualsWithDifferentName() {
                UpdateStatsRequest.PlayerDTO basePlayer = new UpdateStatsRequest.PlayerDTO(
                                "id1", "player1", "session1", 1, 10.0, 20.0);

                UpdateStatsRequest.PlayerDTO differentPlayer = new UpdateStatsRequest.PlayerDTO(
                                "id1", "player2", "session1", 1, 10.0, 20.0);

                assertNotEquals(basePlayer, differentPlayer);
        }

        @Test
        void testPlayerDTOEqualsWithDifferentSessionId() {
                UpdateStatsRequest.PlayerDTO basePlayer = new UpdateStatsRequest.PlayerDTO(
                                "id1", "player1", "session1", 1, 10.0, 20.0);

                UpdateStatsRequest.PlayerDTO differentPlayer = new UpdateStatsRequest.PlayerDTO(
                                "id1", "player1", "session2", 1, 10.0, 20.0);

                assertNotEquals(basePlayer, differentPlayer);
        }

        // Repite el patrón para team, x, y
        @Test
        void testPlayerDTOEqualsWithDifferentTeam() {
                UpdateStatsRequest.PlayerDTO basePlayer = new UpdateStatsRequest.PlayerDTO(
                                "id1", "player1", "session1", 1, 10.0, 20.0);

                UpdateStatsRequest.PlayerDTO differentPlayer = new UpdateStatsRequest.PlayerDTO(
                                "id1", "player1", "session1", 2, 10.0, 20.0);

                assertNotEquals(basePlayer, differentPlayer);
        }

        @Test
        void testPlayerDTOEqualsWithDifferentX() {
                UpdateStatsRequest.PlayerDTO basePlayer = new UpdateStatsRequest.PlayerDTO(
                                "id1", "player1", "session1", 1, 10.0, 20.0);

                UpdateStatsRequest.PlayerDTO differentPlayer = new UpdateStatsRequest.PlayerDTO(
                                "id1", "player1", "session1", 1, 99.9, 20.0);

                assertNotEquals(basePlayer, differentPlayer);
        }

        @Test
        void testPlayerDTOEqualsWithDifferentY() {
                UpdateStatsRequest.PlayerDTO basePlayer = new UpdateStatsRequest.PlayerDTO(
                                "id1", "player1", "session1", 1, 10.0, 20.0);

                UpdateStatsRequest.PlayerDTO differentPlayer = new UpdateStatsRequest.PlayerDTO(
                                "id1", "player1", "session1", 1, 10.0, 99.9);

                assertNotEquals(basePlayer, differentPlayer);
        }

        // Prueba con valores null
        @Test
        void testPlayerDTOEqualsWithNullId() {
                UpdateStatsRequest.PlayerDTO player1 = new UpdateStatsRequest.PlayerDTO(
                                null, "player1", "session1", 1, 10.0, 20.0);

                UpdateStatsRequest.PlayerDTO player2 = new UpdateStatsRequest.PlayerDTO(
                                "id1", "player1", "session1", 1, 10.0, 20.0);

                assertNotEquals(player1, player2);
        }

        // Prueba con mismo objeto
        @Test
        void testPlayerDTOEqualsSameInstance() {
                UpdateStatsRequest.PlayerDTO player = new UpdateStatsRequest.PlayerDTO(
                                "id1", "player1", "session1", 1, 10.0, 20.0);

                assertEquals(player, player);
        }

        // Prueba con clase diferente
        @Test
        void testPlayerDTOEqualsDifferentClass() {
                UpdateStatsRequest.PlayerDTO player = new UpdateStatsRequest.PlayerDTO(
                                "id1", "player1", "session1", 1, 10.0, 20.0);

                assertNotEquals(player, "Soy un String, no un PlayerDTO");
        }

        // Prueba de hashCode con campos null
        @Test
        void testPlayerDTOHashCodeWithNulls() {
                UpdateStatsRequest.PlayerDTO player = new UpdateStatsRequest.PlayerDTO(
                                null, null, null, 0, 0.0, 0.0);

                assertDoesNotThrow(player::hashCode); // No debe lanzar NPE
        }

        // Pruebas para equals() y hashCode() de PlayerDTO
        @Test
        void testPlayerDTOEquals_AllFieldsSame_ShouldBeEqual() {
                PlayerDTO player1 = new PlayerDTO("1", "Alice", "s1", 1, 10.5, 20.5);
                PlayerDTO player2 = new PlayerDTO("1", "Alice", "s1", 1, 10.5, 20.5);
                assertEquals(player1, player2);
                assertEquals(player1.hashCode(), player2.hashCode());
        }

        @Test
        void testPlayerDTOEquals_DifferentId_ShouldNotBeEqual() {
                PlayerDTO base = new PlayerDTO("1", "Bob", "s2", 2, 5.5, 15.5);
                PlayerDTO different = new PlayerDTO("2", "Bob", "s2", 2, 5.5, 15.5);
                assertNotEquals(base, different);
        }

        @Test
        void testPlayerDTOEquals_DifferentName_ShouldNotBeEqual() {
                PlayerDTO base = new PlayerDTO("1", "Bob", "s2", 2, 5.5, 15.5);
                PlayerDTO different = new PlayerDTO("1", "Charlie", "s2", 2, 5.5, 15.5);
                assertNotEquals(base, different);
        }

        @Test
        void testPlayerDTOEquals_DifferentSessionId_ShouldNotBeEqual() {
                PlayerDTO base = new PlayerDTO("1", "Bob", "s2", 2, 5.5, 15.5);
                PlayerDTO different = new PlayerDTO("1", "Bob", "s3", 2, 5.5, 15.5);
                assertNotEquals(base, different);
        }

        @Test
        void testPlayerDTOEquals_DifferentTeam_ShouldNotBeEqual() {
                PlayerDTO base = new PlayerDTO("1", "Bob", "s2", 2, 5.5, 15.5);
                PlayerDTO different = new PlayerDTO("1", "Bob", "s2", 3, 5.5, 15.5);
                assertNotEquals(base, different);
        }

        @Test
        void testPlayerDTOEquals_DifferentX_ShouldNotBeEqual() {
                PlayerDTO base = new PlayerDTO("1", "Bob", "s2", 2, 5.5, 15.5);
                PlayerDTO different = new PlayerDTO("1", "Bob", "s2", 2, 5.6, 15.5);
                assertNotEquals(base, different);
        }

        @Test
        void testPlayerDTOEquals_DifferentY_ShouldNotBeEqual() {
                PlayerDTO base = new PlayerDTO("1", "Bob", "s2", 2, 5.5, 15.5);
                PlayerDTO different = new PlayerDTO("1", "Bob", "s2", 2, 5.5, 15.6);
                assertNotEquals(base, different);
        }

        @Test
        void testPlayerDTOEquals_NullComparison_ShouldNotBeEqual() {
                PlayerDTO player = new PlayerDTO("1", "Dave", "s4", 1, 0.0, 0.0);
                assertNotEquals(null, player);
        }

        @Test
        void testPlayerDTOEquals_DifferentObjectType_ShouldNotBeEqual() {
                PlayerDTO player = new PlayerDTO("1", "Dave", "s4", 1, 0.0, 0.0);
                assertNotEquals(player, "Not a PlayerDTO");
        }

        @Test
        void testPlayerDTOEquals_SameInstance_ShouldBeEqual() {
                PlayerDTO player = new PlayerDTO("1", "Eve", "s5", 2, 7.7, 8.8);
                assertEquals(player, player);
        }

        @Test
        void testPlayerDTOHashCode_Consistency_ShouldMatch() {
                PlayerDTO player = new PlayerDTO("1", "Frank", "s6", 3, 9.9, 10.1);
                int initialHash = player.hashCode();
                assertEquals(initialHash, player.hashCode());
        }

        @Test
        void testPlayerDTOEquals_NullFields_ShouldBeEqual() {
                PlayerDTO player1 = new PlayerDTO(null, null, null, 0, 0.0, 0.0);
                PlayerDTO player2 = new PlayerDTO(null, null, null, 0, 0.0, 0.0);
                assertEquals(player1, player2);
                assertEquals(player1.hashCode(), player2.hashCode());
        }

        @Test
        void testPlayerDTOEquals_MixedNullAndNonNull_ShouldNotBeEqual() {
                PlayerDTO player1 = new PlayerDTO(null, "Name", "s7", 1, 1.1, 2.2);
                PlayerDTO player2 = new PlayerDTO("1", "Name", "s7", 1, 1.1, 2.2);
                assertNotEquals(player1, player2);
        }

        @Test
        void testPlayerDTOEquals_DoublePrecision_ShouldBeEqual() {
                PlayerDTO player1 = new PlayerDTO("1", "Geo", "s8", 4, 3.1415926535, 2.7182818284);
                PlayerDTO player2 = new PlayerDTO("1", "Geo", "s8", 4, 3.1415926535, 2.7182818284);
                assertEquals(player1, player2);
        }

        @Test
        void testPlayerDTOHashCode_NullFields_ShouldNotThrow() {
                PlayerDTO player = new PlayerDTO(null, null, null, 0, 0.0, 0.0);
                assertDoesNotThrow(player::hashCode);
        }

}
