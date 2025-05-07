package co.edu.eci.pigball.user.service;

import co.edu.eci.pigball.user.model.request.UpdateStatsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.eci.pigball.user.dto.CreateUserDTO;
import co.edu.eci.pigball.user.dto.FriendResponseDTO;
import co.edu.eci.pigball.user.dto.UpdateUserDTO;
import co.edu.eci.pigball.user.dto.UserResponseDTO;
import co.edu.eci.pigball.user.dto.UserSummaryDTO;
import co.edu.eci.pigball.user.dto.UsersResponse;
import co.edu.eci.pigball.user.exception.BlogAppException;
import co.edu.eci.pigball.user.exception.DuplicateResourceException;
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

        userRepository.findById(userDTO.getId())
            .ifPresent(u -> {
                throw new DuplicateResourceException("User","Id" ,userDTO.getId());
            });

        userRepository.findByUsername(userDTO.getUsername())
            .ifPresent(u -> {
                throw new DuplicateResourceException("User","Username" ,userDTO.getUsername());
            });

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
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

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
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id",userId));
    }

        // Obtener usuario por username
        public UserResponseDTO getUserByUsername(String username) {
            return userRepository.findByUsername(username)
                    .map(this::convertToDTO)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "Username",username));
        }

    // Actualizar todos los campos de un usuario
    public UserResponseDTO updateUser(String userId, UpdateUserDTO userDTO) {

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","Id" ,userId));

        // Actualiza solo los campos no nulos del DTO
        if (userDTO.getUsername() != null && !userDTO.getUsername().equals(existingUser.getUsername())) {
            userRepository.findByUsername(userDTO.getUsername())
            .ifPresent(u -> {
                throw new DuplicateResourceException("User","Username" ,userDTO.getUsername());
            });
            existingUser.setUsername(userDTO.getUsername());
        }
        Optional.ofNullable(userDTO.getImage()).ifPresent(existingUser::setImage);
        Optional.ofNullable(userDTO.getIconType()).ifPresent(existingUser::setIconType);
        Optional.ofNullable(userDTO.getBorderColor()).ifPresent(existingUser::setBorderColor);
        Optional.ofNullable(userDTO.getCenterColor()).ifPresent(existingUser::setCenterColor);
        Optional.ofNullable(userDTO.getIconColor()).ifPresent(existingUser::setIconColor);

        User updatedUser = userRepository.save(existingUser);
        return convertToDTO(updatedUser);
    }

    // Eliminar usuario por ID
    public void deleteUser(String userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id",userId));

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

    public List<UserSummaryDTO> getAllUserSummaries(List<String> ids) {
        return userRepository.findAllUserSummaries(ids);
    }

    public UsersResponse findPotentialFriends(
        String currentUserId, String searchTerm, int pageNumber, int pageSize, String sortBy, String sortDir) {
    
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) 
            ? Sort.by(sortBy).ascending() 
            : Sort.by(sortBy).descending();
        
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        
        Page<User> usersPage;
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            usersPage = userRepository.findByUsernameContainingIgnoreCaseAndIdNot(
                    searchTerm.trim(), currentUserId, pageable);
        } else {
            usersPage = userRepository.findByIdNot(currentUserId, pageable);
        }
        
        List<UserResponseDTO> content = usersPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        UsersResponse usersResponse = UsersResponse.builder()
            .users(content)
            .pagesNo(usersPage.getNumber())
            .pageSize(usersPage.getSize())
            .totalPages(usersPage.getTotalPages())
            .lastOne(usersPage.isLast())
            .totalElements(usersPage.getTotalElements())
            .build();

        return usersResponse;
    }
    @Transactional
    public FriendResponseDTO addFriend(String userId, String friendId) {
        
        if (userId.equals(friendId)) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "You can't add yourself as a friend.");}

        List<User> users = userRepository.findByIdIn(List.of(userId, friendId));
        if (users.size() != 2) {
            String missingId = users.stream()
                .map(User::getId)
                .noneMatch(userId::equals) ? userId : friendId;
            
            throw new ResourceNotFoundException("User", "id", missingId);
        }

        User user = users.get(0).getId().equals(userId) ? users.get(0) : users.get(1);
        User friend = users.get(0).getId().equals(friendId) ? users.get(0) : users.get(1);

        if (user.getFriendsIds().contains(friendId)) {
            throw new BlogAppException(HttpStatus.CONFLICT, String.format("You are already friends with %s", friend.getUsername()));
        }

        user.addFriendId(friendId);
        
        User updatedUser = userRepository.save(user);
        
        return FriendResponseDTO.added(userId, friendId, updatedUser.getFriendsIds().size());
    }

    public FriendResponseDTO removeFriend(String userId, String friendId) {
        // Obtener el usuario actual
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // Comprobar si realmente es amigo
        if (!user.getFriendsIds().contains(friendId)) {
            throw new BlogAppException(HttpStatus.CONFLICT, String.format("You are not friends with: ", friendId));
        }


        // Eliminar amigo
        user.removeFriendId(friendId);

        User updatedUser = userRepository.save(user);
        
        // Guardar los cambios
        return FriendResponseDTO.removed(userId, friendId, updatedUser.getFriendsIds().size());
    }

    public List<UserSummaryDTO> getFriendsList(String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        
        return getAllUserSummaries(new ArrayList<>(user.getFriendsIds()));
    }

}