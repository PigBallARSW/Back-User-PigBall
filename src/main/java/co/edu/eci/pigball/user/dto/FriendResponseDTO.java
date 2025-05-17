package co.edu.eci.pigball.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendResponseDTO {
    private String userId;
    private String friendId;
    private String operation; // "ADDED" or "REMOVED"
    private int totalFriends;
    private String message;

    /**
     * Factory method to build a FriendResponse when a friend is added.
     */
    public static FriendResponseDTO added(String userId, String friendId, int totalFriends) {

        return FriendResponseDTO.builder()
            .userId(userId)
            .friendId(friendId)
            .operation("ADDED")
            .totalFriends(totalFriends)
            .message("User " + friendId + " added successfully.")
            .build();
    }

    /**
     * Factory method to build a FriendResponse when a friend is removed.
     */
    public static FriendResponseDTO removed(String userId, String friendId, int totalFriends) {
        
        return FriendResponseDTO.builder()
            .userId(userId)
            .friendId(friendId)
            .operation("REMOVED")
            .totalFriends(totalFriends)
            .message("User " + friendId + " removed successfully.")
            .build();
    }
}
