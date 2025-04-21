package co.edu.eci.pigball.user.service;

import java.util.List;

import co.edu.eci.pigball.user.dto.CreateUserDTO;
import co.edu.eci.pigball.user.dto.UpdateUserDTO;
import co.edu.eci.pigball.user.dto.UserResponseDTO;
import co.edu.eci.pigball.user.model.request.UpdateStatsRequest;

public interface UserService {
    public UserResponseDTO createUser(CreateUserDTO userDTO);
    public UserResponseDTO updateUserStats(String userId, int score, boolean isWinner);
    public UserResponseDTO getUserById(String userId);
    public UserResponseDTO getUserByUsername(String username);
    public List<UserResponseDTO> getAllUsers();
    public void deleteUser(String userId);
    public UserResponseDTO updateUser(String userId, UpdateUserDTO userDTO);
    public String updateStats(UpdateStatsRequest request);
}
