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
                () -> assertEquals(expectedMessage, dto.getMessage())
        );
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
                () -> assertEquals(expectedMessage, dto.getMessage())
        );
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
                () -> assertNotEquals(dto1.hashCode(), differentDto.hashCode())
        );
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
                () -> assertTrue(toStringResult.contains("Test message"))
        );
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
                () -> assertEquals("User " + friendId + " added successfully.", dto.getMessage())
        );
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
                () -> assertEquals("User " + friendId + " removed successfully.", dto.getMessage())
        );
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
                () -> assertEquals("REMOVED", removedDto.getOperation())
        );
    }
}