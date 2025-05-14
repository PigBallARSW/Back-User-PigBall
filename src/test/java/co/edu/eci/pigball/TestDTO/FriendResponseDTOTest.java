package co.edu.eci.pigball.TestDTO;

import org.junit.jupiter.api.Test;

import co.edu.eci.pigball.user.dto.FriendResponseDTO;

import static org.junit.jupiter.api.Assertions.*;

class FriendResponseDTOTest {

        @Test
        void testBuilderAndGetters() {
                // Arrange
                String expectedUserId = "user123";
                String expectedFriendId = "friend456";
                String expectedOperation = "ADDED";
                int expectedTotalFriends = 5;
                String expectedMessage = "Test message";

                // Act
                FriendResponseDTO dto = FriendResponseDTO.builder()
                                .userId(expectedUserId)
                                .friendId(expectedFriendId)
                                .operation(expectedOperation)
                                .totalFriends(expectedTotalFriends)
                                .message(expectedMessage)
                                .build();

                // Assert
                assertAll(
                                () -> assertEquals(expectedUserId, dto.getUserId()),
                                () -> assertEquals(expectedFriendId, dto.getFriendId()),
                                () -> assertEquals(expectedOperation, dto.getOperation()),
                                () -> assertEquals(expectedTotalFriends, dto.getTotalFriends()),
                                () -> assertEquals(expectedMessage, dto.getMessage()));
        }

        @Test
        void testSetters() {
                // Arrange
                FriendResponseDTO dto = FriendResponseDTO.builder().build();
                String expectedUserId = "newUser123";
                String expectedFriendId = "newFriend456";
                String expectedOperation = "REMOVED";
                int expectedTotalFriends = 3;
                String expectedMessage = "New test message";

                // Act
                dto.setUserId(expectedUserId);
                dto.setFriendId(expectedFriendId);
                dto.setOperation(expectedOperation);
                dto.setTotalFriends(expectedTotalFriends);
                dto.setMessage(expectedMessage);

                // Assert
                assertAll(
                                () -> assertEquals(expectedUserId, dto.getUserId()),
                                () -> assertEquals(expectedFriendId, dto.getFriendId()),
                                () -> assertEquals(expectedOperation, dto.getOperation()),
                                () -> assertEquals(expectedTotalFriends, dto.getTotalFriends()),
                                () -> assertEquals(expectedMessage, dto.getMessage()));
        }

        @Test
        void testEqualsAndHashCode() {
                // Arrange
                FriendResponseDTO dto1 = FriendResponseDTO.builder()
                                .userId("user1")
                                .friendId("friend1")
                                .operation("ADDED")
                                .totalFriends(2)
                                .message("Added friend1")
                                .build();

                FriendResponseDTO dto2 = FriendResponseDTO.builder()
                                .userId("user1")
                                .friendId("friend1")
                                .operation("ADDED")
                                .totalFriends(2)
                                .message("Added friend1")
                                .build();

                FriendResponseDTO differentDto = FriendResponseDTO.builder()
                                .userId("user2")
                                .friendId("friend2")
                                .operation("REMOVED")
                                .totalFriends(1)
                                .message("Removed friend2")
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
                FriendResponseDTO dto = FriendResponseDTO.builder()
                                .userId("user123")
                                .friendId("friend456")
                                .operation("ADDED")
                                .totalFriends(3)
                                .message("Test message")
                                .build();

                // Act
                String toStringResult = dto.toString();

                // Assert
                assertAll(
                                () -> assertTrue(toStringResult.contains("user123")),
                                () -> assertTrue(toStringResult.contains("friend456")),
                                () -> assertTrue(toStringResult.contains("ADDED")),
                                () -> assertTrue(toStringResult.contains("3")),
                                () -> assertTrue(toStringResult.contains("Test message")));
        }

        @Test
        void testAddedFactoryMethod() {
                // Arrange
                String userId = "user123";
                String friendId = "friend456";
                int totalFriends = 5;

                // Act
                FriendResponseDTO dto = FriendResponseDTO.added(userId, friendId, totalFriends);

                // Assert
                assertAll(
                                () -> assertEquals(userId, dto.getUserId()),
                                () -> assertEquals(friendId, dto.getFriendId()),
                                () -> assertEquals("ADDED", dto.getOperation()),
                                () -> assertEquals(totalFriends, dto.getTotalFriends()),
                                () -> assertEquals("User " + friendId + " added successfully.", dto.getMessage()));
        }

        @Test
        void testRemovedFactoryMethod() {
                // Arrange
                String userId = "user123";
                String friendId = "friend789";
                int totalFriends = 2;

                // Act
                FriendResponseDTO dto = FriendResponseDTO.removed(userId, friendId, totalFriends);

                // Assert
                assertAll(
                                () -> assertEquals(userId, dto.getUserId()),
                                () -> assertEquals(friendId, dto.getFriendId()),
                                () -> assertEquals("REMOVED", dto.getOperation()),
                                () -> assertEquals(totalFriends, dto.getTotalFriends()),
                                () -> assertEquals("User " + friendId + " removed successfully.", dto.getMessage()));
        }

        @Test
        void testFactoryMethodsCreateDifferentResponses() {
                // Arrange
                String userId = "user123";
                String friendId = "friend456";
                int totalFriends = 3;

                // Act
                FriendResponseDTO addedDto = FriendResponseDTO.added(userId, friendId, totalFriends);
                FriendResponseDTO removedDto = FriendResponseDTO.removed(userId, friendId, totalFriends);

                // Assert
                assertAll(
                                () -> assertNotEquals(addedDto.getOperation(), removedDto.getOperation()),
                                () -> assertNotEquals(addedDto.getMessage(), removedDto.getMessage()),
                                () -> assertEquals("ADDED", addedDto.getOperation()),
                                () -> assertEquals("REMOVED", removedDto.getOperation()));
        }

        // Pruebas para equals() y hashCode()
        @Test
        void testEquals_SameInstance_ReturnsTrue() {
                FriendResponseDTO dto = FriendResponseDTO.builder().userId("123").build();
                assertEquals(dto, dto);
        }

        @Test
        void testEquals_NullComparison_ReturnsFalse() {
                FriendResponseDTO dto = FriendResponseDTO.builder().friendId("456").build();
                assertNotEquals(null, dto);
        }

        @Test
        void testEquals_DifferentClass_ReturnsFalse() {
                FriendResponseDTO dto = FriendResponseDTO.builder().build();
                assertNotEquals(dto, "Not a FriendResponseDTO");
        }

        @Test
        void testEquals_AllFieldsNull_Equal() {
                FriendResponseDTO dto1 = FriendResponseDTO.builder().build();
                FriendResponseDTO dto2 = FriendResponseDTO.builder().build();
                assertEquals(dto1, dto2);
        }

        // Pruebas por cada campo individual
        @Test
        void testEquals_DifferentUserId_NotEqual() {
                FriendResponseDTO dto1 = FriendResponseDTO.builder().userId("A").build();
                FriendResponseDTO dto2 = FriendResponseDTO.builder().userId("B").build();
                assertNotEquals(dto1, dto2);
        }

        @Test
        void testEquals_DifferentFriendId_NotEqual() {
                FriendResponseDTO dto1 = FriendResponseDTO.builder().friendId("X").build();
                FriendResponseDTO dto2 = FriendResponseDTO.builder().friendId("Y").build();
                assertNotEquals(dto1, dto2);
        }

        @Test
        void testEquals_DifferentOperation_NotEqual() {
                FriendResponseDTO dto1 = FriendResponseDTO.builder().operation("ADDED").build();
                FriendResponseDTO dto2 = FriendResponseDTO.builder().operation("REMOVED").build();
                assertNotEquals(dto1, dto2);
        }

        @Test
        void testEquals_DifferentTotalFriends_NotEqual() {
                FriendResponseDTO dto1 = FriendResponseDTO.builder().totalFriends(2).build();
                FriendResponseDTO dto2 = FriendResponseDTO.builder().totalFriends(5).build();
                assertNotEquals(dto1, dto2);
        }

        @Test
        void testEquals_DifferentMessage_NotEqual() {
                FriendResponseDTO dto1 = FriendResponseDTO.builder().message("Msg1").build();
                FriendResponseDTO dto2 = FriendResponseDTO.builder().message("Msg2").build();
                assertNotEquals(dto1, dto2);
        }

        @Test
        void testEquals_NullVsNonNullFields_NotEqual() {
                FriendResponseDTO dto1 = FriendResponseDTO.builder().userId(null).build();
                FriendResponseDTO dto2 = FriendResponseDTO.builder().userId("123").build();
                assertNotEquals(dto1, dto2);
        }

        // Pruebas de hashCode()
        @Test
        void testHashCode_Consistency_ShouldMatch() {
                FriendResponseDTO dto = FriendResponseDTO.added("u1", "f1", 3);
                int initialHash = dto.hashCode();
                assertEquals(initialHash, dto.hashCode());
        }

        @Test
        void testHashCode_DifferentObjects_SameHash() {
                FriendResponseDTO dto1 = FriendResponseDTO.builder()
                                .userId("u1")
                                .friendId("f1")
                                .operation("ADDED")
                                .totalFriends(2)
                                .message("Added")
                                .build();

                FriendResponseDTO dto2 = FriendResponseDTO.builder()
                                .userId("u1")
                                .friendId("f1")
                                .operation("ADDED")
                                .totalFriends(2)
                                .message("Added")
                                .build();

                assertEquals(dto1.hashCode(), dto2.hashCode());
        }

        @Test
        void testHashCode_DifferentFields_DifferentHash() {
                FriendResponseDTO dto1 = FriendResponseDTO.builder().totalFriends(2).build();
                FriendResponseDTO dto2 = FriendResponseDTO.builder().totalFriends(5).build();
                assertNotEquals(dto1.hashCode(), dto2.hashCode());
        }

        // Casos especiales
        @Test
        void testEquals_SameFactoryMethods_Equal() {
                FriendResponseDTO dto1 = FriendResponseDTO.added("u1", "f1", 3);
                FriendResponseDTO dto2 = FriendResponseDTO.added("u1", "f1", 3);
                assertEquals(dto1, dto2);
        }

        @Test
        void testEquals_DifferentMessageSameFactory_NotEqual() {
                FriendResponseDTO dto1 = FriendResponseDTO.added("u1", "f1", 3);
                FriendResponseDTO dto2 = FriendResponseDTO.added("u1", "f1", 3);
                dto2.setMessage("Modified message");
                assertNotEquals(dto1, dto2);
        }

        @Test
        void testHashCode_WithNullFields_DoesNotThrow() {
                FriendResponseDTO dto = FriendResponseDTO.builder()
                                .userId(null)
                                .friendId(null)
                                .operation(null)
                                .build();

                assertDoesNotThrow(dto::hashCode);
        }

        @Test
        void testFriendResponseDTOBuilderToString() {
                // Configurar valores en el builder
                FriendResponseDTO.FriendResponseDTOBuilder builder = FriendResponseDTO.builder()
                                .userId("user123")
                                .friendId("friend456")
                                .operation("ADDED")
                                .totalFriends(5)
                                .message("Test message");

                // Obtener representación String del builder
                String builderString = builder.toString();

                // Verificar que los campos configurados están presentes
                assertAll(
                                () -> assertTrue(builderString.contains("userId=user123"),
                                                "Debe mostrar el userId correcto"),
                                () -> assertTrue(builderString.contains("friendId=friend456"),
                                                "Debe contener el friendId"),
                                () -> assertTrue(builderString.contains("operation=ADDED"),
                                                "Debe mostrar la operación"),
                                () -> assertTrue(builderString.contains("totalFriends=5"),
                                                "Debe mostrar el total de amigos"),
                                () -> assertTrue(builderString.contains("message=Test message"),
                                                "Debe incluir el mensaje"));
        }
}