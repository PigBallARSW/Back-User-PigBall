package co.edu.eci.pigball.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.eci.pigball.user.dto.CreateUserDTO;
import co.edu.eci.pigball.user.dto.UpdateUserDTO;
import co.edu.eci.pigball.user.dto.UserResponseDTO;
import co.edu.eci.pigball.user.exception.ResourceNotFoundException;
import co.edu.eci.pigball.user.model.User;
import co.edu.eci.pigball.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    // Crear usuario
    public UserResponseDTO createUser(CreateUserDTO userDTO) {

        if (userRepository.existsById(userDTO.getId())) {
            throw new RuntimeException("El ID ya existe");
        }

        User user = User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .build();

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    // Actualizar estadísticas (existente)
    public UserResponseDTO updateUserStats(String userId, int score, boolean isWinner) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", userId));

        user.addToTotalScore(score);
        user.updateBestScore(score); 

        if (isWinner) {
            user.incrementGamesWon();
        } else {
            user.incrementLostGames();
        }
        
        return convertToDTO(userRepository.save(user));
    }

        // Listar todos los usuarios
        public List<UserResponseDTO> getAllUsers() {
            return userRepository.findAll()
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }    

    // Obtener usuario por ID
    public UserResponseDTO getUserById(String userId) {
        return userRepository.findById(userId)
                .map(this::convertToDTO)
                .orElseThrow(() ->  new ResourceNotFoundException("Usuario", userId));
    }

    // Obtener usuario por username
    public UserResponseDTO getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::convertToDTO)
                .orElseThrow(() ->  new ResourceNotFoundException("Usuario", username));
    }


    // Actualizar todos los campos de un usuario
    public UserResponseDTO updateUser(String userId, UpdateUserDTO userDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", userId));
       
        existingUser.setUsername(userDTO.getUsername());
      
        User updatedUser = userRepository.save(existingUser);
        return convertToDTO(updatedUser);
    }

        // Eliminar usuario por ID
        public void deleteUser(String userId) {
            userRepository.findById(userId)
                .orElseThrow(() ->  new ResourceNotFoundException("Usuario", userId));
            
            userRepository.deleteById(userId);
        }

    // Conversor de Entidad a DTO (privado)
    private UserResponseDTO convertToDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername()) 
                .lostGames(user.getLostGames())
                .gamesWon(user.getGamesWon())
                .totalScore(user.getTotalScore())
                .bestScore(user.getBestScore())
                .gamesPlayed(user.getGamesPlayed())  // Calculado
                .winningPercentage(user.getWinningPercentage())  // Calculado
                .build();
    }
}