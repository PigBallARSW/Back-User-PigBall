package co.edu.eci.pigball.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.eci.pigball.user.Model.User;
import co.edu.eci.pigball.user.dto.UserDTO;
import co.edu.eci.pigball.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Crear usuario
    public UserDTO createUser(UserDTO userDTO) {
        User user = User.builder()
                .username(userDTO.getUsername())
                // Se omiten gamesPlayed y winningPercentage ya que son calculados
                .lostGames(userDTO.getLostGames())
                .gamesWon(userDTO.getGamesWon())
                .totalScore(userDTO.getTotalScore())
                .bestScore(userDTO.getBestScore())
                .build();

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    // Actualizar estadísticas (existente)
    public UserDTO updateUserStats(String userId, int score, boolean isWinner) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // No se suma manualmente gamesPlayed, ya que se calcula como lostGames + gamesWon
        user.addToTotalScore(score);
        if (isWinner) {
            user.incrementGamesWon();
        } else {
            user.incrementLostGames();
        }
        if (score > user.getBestScore()) {
            user.updateBestScore(score);
        }

        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    // Obtener usuario por ID
    public UserDTO getUserById(String userId) {
        return userRepository.findById(userId)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // Obtener usuario por username
    public UserDTO getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // Listar todos los usuarios
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Eliminar usuario por ID
    public void deleteUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        userRepository.deleteById(userId);
    }

    // Actualizar todos los campos de un usuario
    public UserDTO updateUser(String userId, UserDTO userDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Se actualiza solo el username y los contadores base
        existingUser.setUsername(userDTO.getUsername());
        existingUser.setLostGames(userDTO.getLostGames());
        existingUser.setGamesWon(userDTO.getGamesWon());
        existingUser.setTotalScore(userDTO.getTotalScore());
        existingUser.setBestScore(userDTO.getBestScore());
        // gamesPlayed y winningPercentage se calcularán automáticamente

        User updatedUser = userRepository.save(existingUser);
        return convertToDTO(updatedUser);
    }

    // Conversor de Entidad a DTO (privado)
    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername()) // Calculado
                .lostGames(user.getLostGames())
                .gamesWon(user.getGamesWon())
                .totalScore(user.getTotalScore())
                .bestScore(user.getBestScore())
                .build();
    }
}