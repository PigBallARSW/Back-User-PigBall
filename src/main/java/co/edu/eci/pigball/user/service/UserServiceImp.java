package co.edu.eci.pigball.user.service;

import co.edu.eci.pigball.user.model.request.UpdateStatsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.eci.pigball.user.dto.CreateUserDTO;
import co.edu.eci.pigball.user.dto.UpdateUserDTO;
import co.edu.eci.pigball.user.dto.UserResponseDTO;
import co.edu.eci.pigball.user.exception.ResourceNotFoundException;
import co.edu.eci.pigball.user.model.User;
import co.edu.eci.pigball.user.repository.UserRepository;

import java.util.*;
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
                .image(userDTO.getImage())
                .iconType(userDTO.getIconType())
                .borderColor(userDTO.getBorderColor())
                .centerColor(userDTO.getCenterColor())
                .iconColor(userDTO.getIconColor())
                .iconType(userDTO.getIconType())
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
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", userId));
    }

    // Obtener usuario por username
    public UserResponseDTO getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", username));
    }

    // Actualizar todos los campos de un usuario
    public UserResponseDTO updateUser(String userId, UpdateUserDTO userDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", userId));

        // Actualiza solo los campos no nulos del DTO
        if (userDTO.getUsername() != null) {
            existingUser.setUsername(userDTO.getUsername());
        }
        if (userDTO.getImage() != null) {
            existingUser.setImage(userDTO.getImage());
        }
        if (userDTO.getIconType() != null) {
            existingUser.setIconType(userDTO.getIconType());
        }
        if (userDTO.getBorderColor() != null) {
            existingUser.setBorderColor(userDTO.getBorderColor());
        }
        if (userDTO.getCenterColor() != null) {
            existingUser.setCenterColor(userDTO.getCenterColor());
        }
        if (userDTO.getIconColor() != null) {
            existingUser.setIconColor(userDTO.getIconColor());
        }
        if (userDTO.getIconType() != null) {
            existingUser.setIconType(userDTO.getIconType());
        }

        User updatedUser = userRepository.save(existingUser);
        return convertToDTO(updatedUser);
    }

    // Eliminar usuario por ID
    public void deleteUser(String userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", userId));

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
                .gamesPlayed(user.getGamesPlayed()) // Calculado
                .winningPercentage(user.getWinningPercentage()) // Calculado
                .image(user.getImage())
                .iconType(user.getIconType())
                .borderColor(user.getBorderColor())
                .centerColor(user.getCenterColor())
                .iconType(user.getIconType())
                .iconColor(user.getIconColor())
                .build();
    }

    public String updateStats(UpdateStatsRequest request) {
        Map<String, Integer> pointsMap = Map.of(
                "GOAL_SCORED", 20,
                "SELF_GOAL_SCORED", -10,
                "GOAL_ASSIST", 10);

        Map<Integer, Integer> teamGoals = new HashMap<>();
        teamGoals.put(0, 0);
        teamGoals.put(1, 0);

        for (UpdateStatsRequest.Stat stat : request.getStats()) {
            String playerId = stat.getFirst();
            String event = stat.getSecond();

            UpdateStatsRequest.PlayerDTO player = request.getPlayers()
                    .stream()
                    .filter(p -> p.getId().equals(playerId))
                    .findFirst()
                    .orElse(null);

            if (player == null)
                continue;

            // Buscar usuario por ID en base de datos
            Optional<User> userOpt = userRepository.findById(playerId);
            if (userOpt.isEmpty())
                continue;

            User user = userOpt.get();

            int points = pointsMap.getOrDefault(event, 0);

            user.addToTotalScore(points);
            user.updateBestScore(points);

            // Sumar goles por equipo si fue un GOAL_SCORED
            if ("GOAL_SCORED".equals(event)) {
                teamGoals.put(player.getTeam(), teamGoals.get(player.getTeam()) + 1);
            }

            userRepository.save(user);
        }

        int goalsTeam0 = teamGoals.get(0);
        int goalsTeam1 = teamGoals.get(1);

        String result;
        if (goalsTeam0 > goalsTeam1) {

            updateGameStats(request, 0, true); // Team 0 ganó
            updateGameStats(request, 1, false); // Team 1 perdió
        } else if (goalsTeam1 > goalsTeam0) {

            updateGameStats(request, 0, false); // Team 0 perdió
            updateGameStats(request, 1, true); // Team 1 ganó
        }

        return "statistics update successful";
    }

    private void updateGameStats(UpdateStatsRequest request, int team, boolean teamWon) {
        List<User> usersToUpdate = new ArrayList<>();
        for (UpdateStatsRequest.PlayerDTO player : request.getPlayers()) {
            if (player.getTeam() == team) {
                Optional<User> userOpt = userRepository.findById(player.getId());
                if (userOpt.isPresent()) {
                    User user = userOpt.get();
                    if (teamWon) {
                        user.incrementGamesWon();
                    } else {
                        user.incrementLostGames();
                    }
                    usersToUpdate.add(user);
                }
            }
        }
        userRepository.saveAll(usersToUpdate);
    }
}