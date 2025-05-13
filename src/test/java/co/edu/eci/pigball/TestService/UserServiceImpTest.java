package co.edu.eci.pigball.TestService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;

import co.edu.eci.pigball.user.dto.*;
import co.edu.eci.pigball.user.exception.*;
import co.edu.eci.pigball.user.model.*;
import co.edu.eci.pigball.user.model.request.UpdateStatsRequest;
import co.edu.eci.pigball.user.model.request.UpdateStatsRequest.*;
import co.edu.eci.pigball.user.repository.UserRepository;
import co.edu.eci.pigball.user.service.UserServiceImp;

@ExtendWith(MockitoExtension.class)
public class UserServiceImpTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImp userService;

    // ------------------------- Test para createUser -------------------------
    @Test
    public void testCreateUser_DuplicateId() {
        // Arrange
        CreateUserDTO dto = new CreateUserDTO("1", "jag", "img.png", "typeA", "#FFF", "#000", "#F00");
        when(userRepository.findById("1")).thenReturn(Optional.of(new User()));

        // Act & Assert
        assertThrows(DuplicateResourceException.class, () -> userService.createUser(dto));
    }

    @Test
    public void testCreateUser_Success() {
        // Arrange
        CreateUserDTO dto = new CreateUserDTO("1", "jag", "img.png", "typeA", "#FFF", "#000", "#F00");
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        // Act
        UserResponseDTO result = userService.createUser(dto);

        // Assert
        assertEquals("jag", result.getUsername());
        verify(userRepository).save(any());
    }

    // ------------------------- Test para updateUserStats -------------------------
    @Test
    public void testUpdateUserStats_UserNotFound() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> userService.updateUserStats("1", 100, true));
    }

    @Test
    public void testUpdateUserStats_Success() {
        // Arrange
        User user = User.builder().id("1").totalScore(0).bestScore(0).build();
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);

        // Act
        UserResponseDTO result = userService.updateUserStats("1", 100, true);

        // Assert
        assertEquals(100, result.getTotalScore());
        assertEquals(100, result.getBestScore());
        assertEquals(1, result.getGamesWon());
    }

    // ------------------------- Test para updateStats -------------------------
    @Test
    public void testUpdateStats_GoalScored() {
        // Arrange
        UpdateStatsRequest request = new UpdateStatsRequest();
        request.setStats(List.of(new Stat("player1", "GOAL_SCORED")));
        request.setPlayers(List.of(new PlayerDTO("player1", "Jugador1", "session1", 0, 0, 0)));

        User user = User.builder().id("player1").totalScore(0).bestScore(0).build();
        when(userRepository.findById("player1")).thenReturn(Optional.of(user));

        // Act
        String result = userService.updateStats(request);

        // Assert
        assertEquals("statistics update successful", result);
        assertEquals(20, user.getTotalScore());
        assertEquals(20, user.getBestScore());
        verify(userRepository, atLeastOnce()).save(user);
    }

    // ------------------------- Test para addFriend -------------------------
    @Test
    public void testAddFriend_SelfAddition() {
        assertThrows(BlogAppException.class,
                () -> userService.addFriend("1", "1"));
    }

    @Test
    public void testAddFriend_Success() {
        // Arrange
        User user = User.builder().id("1").friendsIds(new HashSet<>()).build();
        User friend = User.builder().id("2").friendsIds(new HashSet<>()).build();
        when(userRepository.findByIdIn(List.of("1", "2"))).thenReturn(List.of(user, friend));
        when(userRepository.save(any())).thenReturn(user);

        // Act
        FriendResponseDTO response = userService.addFriend("1", "2");

        // Assert
        assertEquals("ADDED", response.getOperation());
        assertTrue(user.getFriendsIds().contains("2"));
        assertTrue(friend.getFriendsIds().contains("1"));
    }

    // ------------------------- Test para removeFriend -------------------------
    @Test
    public void testRemoveFriend_NotFriends() {
        // Arrange
        User user = User.builder()
                .id("1")
                .friendsIds(new HashSet<>()) // Usuario sin amigos
                .build();

        User friend = User.builder()
                .id("2")
                .build();

        // Mockear las llamadas al repositorio
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.findById("2")).thenReturn(Optional.of(friend));

        // Act & Assert
        BlogAppException exception = assertThrows(BlogAppException.class,
                () -> userService.removeFriend("1", "2"));

        // Verificaciones adicionales
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        assertTrue(exception.getMessage().contains("You are not friends with"));
    }

    // ------------------------- Test para findPotentialFriends
    // -------------------------
    @Test
    public void testFindPotentialFriends_WithSearchTerm() {
        // Arrange
        User currentUser = User.builder()
                .id("1")
                .friendsIds(Set.of("2"))
                .build();

        Page<User> page = new PageImpl<>(List.of(
                User.builder().id("3").username("jag").build()));

        // Mockear las llamadas al repositorio
        when(userRepository.findById("1")).thenReturn(Optional.of(currentUser));
        when(userRepository.findByUsernameContainingIgnoreCaseAndIdNotIn(
                anyString(),
                anySet(),
                any(Pageable.class))).thenReturn(page);

        // Act
        UsersResponse response = userService.findPotentialFriends(
                "1", "jag", 0, 10, "username", "asc");

        // Assert
        assertEquals(1, response.getUsers().size());
        assertEquals("jag", response.getUsers().get(0).getUsername());
        verify(userRepository).findById("1");
        verify(userRepository).findByUsernameContainingIgnoreCaseAndIdNotIn(
                eq("jag"),
                eq(Set.of("1", "2")),
                any(Pageable.class));
    }

    // ------------------------- Test para getFriendsList -------------------------
    @Test
    public void testGetFriendsList_NoFriends() {
        User user = User.builder().id("1").friendsIds(Collections.emptySet()).build();
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        UsersResponse response = userService.getFriendsList("1");

        assertTrue(response.getUsers().isEmpty());
    }

    // ------------------------- Test para getAllUsers -------------------------
    @Test
    public void testGetAllUsers_EmptyList() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<UserResponseDTO> result = userService.getAllUsers();

        assertTrue(result.isEmpty());
        verify(userRepository).findAll();
    }

    @Test
    public void testGetAllUsers_WithUsers() {
        // Arrange
        List<User> users = Arrays.asList(
                User.builder().id("1").username("user1").build(),
                User.builder().id("2").username("user2").build());
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<UserResponseDTO> result = userService.getAllUsers();

        // Assert
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        verify(userRepository).findAll();
    }

    // ------------------------- Test para getUserById -------------------------
    @Test
    public void testGetUserById_NotFound() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById("1");
        });
    }

    @Test
    public void testGetUserById_Success() {
        // Arrange
        User user = User.builder().id("1").username("testUser").build();
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        // Act
        UserResponseDTO result = userService.getUserById("1");

        // Assert
        assertEquals("testUser", result.getUsername());
        verify(userRepository).findById("1");
    }

    // ------------------------- Test para getUserByUsername
    // -------------------------
    @Test
    public void testGetUserByUsername_NotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserByUsername("unknown");
        });
    }

    @Test
    public void testGetUserByUsername_Success() {
        // Arrange
        User user = User.builder().username("existingUser").build();
        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(user));

        // Act
        UserResponseDTO result = userService.getUserByUsername("existingUser");

        // Assert
        assertEquals("existingUser", result.getUsername());
        verify(userRepository).findByUsername("existingUser");
    }

    // ------------------------- Test para updateUser -------------------------
    @Test
    public void testUpdateUser_NotFound() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        UpdateUserDTO dto = new UpdateUserDTO();
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.updateUser("1", dto);
        });
    }

    @Test
    public void testUpdateUser_DuplicateUsername() {
        // Arrange
        User existingUser = User.builder()
                .id("1")
                .username("oldUsername")
                .build();

        User otherUser = User.builder()
                .id("2")
                .username("newUsername")
                .build();

        when(userRepository.findById("1")).thenReturn(Optional.of(existingUser));
        when(userRepository.findByUsername("newUsername")).thenReturn(Optional.of(otherUser));

        UpdateUserDTO dto = new UpdateUserDTO();
        dto.setUsername("newUsername");

        // Act & Assert
        assertThrows(DuplicateResourceException.class, () -> {
            userService.updateUser("1", dto);
        });
    }

    @Test
    public void testUpdateUser_Success() {
        // Arrange
        User existingUser = User.builder()
                .id("1")
                .username("oldUser")
                .image("oldImage.png")
                .build();

        when(userRepository.findById("1")).thenReturn(Optional.of(existingUser));
        when(userRepository.findByUsername("newUser")).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        UpdateUserDTO dto = new UpdateUserDTO();
        dto.setUsername("newUser");
        dto.setImage("newImage.png");
        dto.setBorderColor("#123456");

        // Act
        UserResponseDTO result = userService.updateUser("1", dto);

        // Assert
        assertEquals("newUser", result.getUsername());
        assertEquals("newImage.png", result.getImage());
        assertEquals("#123456", result.getBorderColor());
        verify(userRepository).save(existingUser);
    }

    // ------------------------- Test para deleteUser -------------------------
    @Test
    public void testDeleteUser_NotFound() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.deleteUser("1");
        });
        verify(userRepository, never()).deleteById(any());
    }

    @Test
    public void testDeleteUser_Success() {
        // Arrange
        User user = User.builder().id("1").build();
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById("1");

        // Act
        userService.deleteUser("1");

        // Assert
        verify(userRepository).deleteById("1");
    }

    @Test
    public void testAddFriend_AlreadyFriends() {
        // Arrange - Ambos usuarios son amigos
        User user = User.builder()
                .id("1")
                .friendsIds(new HashSet<>(Set.of("2")))
                .build();

        User friend = User.builder()
                .id("2")
                .username("amigo")
                .build();

        when(userRepository.findByIdIn(List.of("1", "2"))).thenReturn(List.of(user, friend));

        // Act & Assert
        BlogAppException exception = assertThrows(BlogAppException.class,
                () -> userService.addFriend("1", "2"));

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        assertTrue(exception.getMessage().contains("already friends with amigo"));
    }

    // @Test
    // public void testAddFriend_UserOrderInResponse() {
    // // Arrange - Verificar orden de usuarios en la respuesta
    // User user1 = User.builder().id("1").friendsIds(new HashSet<>()).build();
    // User user2 = User.builder().id("2").friendsIds(new HashSet<>()).build();

    // // Caso 1: Repositorio devuelve [user1, user2]
    // when(userRepository.findByIdIn(List.of("1", "2"))).thenReturn(List.of(user1,
    // user2));
    // userService.addFriend("1", "2");
    // assertEquals(1, user1.getFriendsIds().size()); // Verifica que user1 fue
    // actualizado

    // // Caso 2: Repositorio devuelve [user2, user1]
    // user1.getFriendsIds().clear();
    // user2.getFriendsIds().clear();
    // when(userRepository.findByIdIn(List.of("1", "2"))).thenReturn(List.of(user2,
    // user1));
    // userService.addFriend("1", "2");
    // assertEquals(1, user1.getFriendsIds().size()); // Verifica que user1 fue
    // actualizado
    // }
    // @Test
    // public void testAddFriend_MissingUserId() {
    // // Arrange - Solo el friend existe
    // User friend = User.builder().id("2").username("friend").build();
    // when(userRepository.findByIdIn(List.of("1",
    // "2"))).thenReturn(List.of(friend));

    // // Act & Assert
    // ResourceNotFoundException exception =
    // assertThrows(ResourceNotFoundException.class,
    // () -> userService.addFriend("1", "2"));

    // assertEquals("User not found with id : 1", exception.getMessage());
    // }

    // @Test
    // public void testAddFriend_MissingFriendId() {
    // // Arrange - Solo el usuario principal existe
    // User user = User.builder().id("1").username("user").build();
    // when(userRepository.findByIdIn(List.of("1", "2"))).thenReturn(List.of(user));

    // // Act & Assert
    // ResourceNotFoundException exception =
    // assertThrows(ResourceNotFoundException.class,
    // () -> userService.addFriend("1", "2"));

    // assertEquals("User not found with id : 2", exception.getMessage());
    // }

    @Test
    public void testAddFriend_UserOrderInResponse() {
        // Arrange
        User user1 = User.builder().id("1").friendsIds(new HashSet<>()).build();
        User user2 = User.builder().id("2").friendsIds(new HashSet<>()).build();

        // Caso 1: Repositorio devuelve [user1, user2]
        when(userRepository.findByIdIn(List.of("1", "2"))).thenReturn(List.of(user1, user2));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        userService.addFriend("1", "2");
        assertEquals(1, user1.getFriendsIds().size()); // Verifica user1 actualizado

        // Caso 2: Repositorio devuelve [user2, user1]
        user1.getFriendsIds().clear();
        user2.getFriendsIds().clear();
        when(userRepository.findByIdIn(List.of("1", "2"))).thenReturn(List.of(user2, user1));

        userService.addFriend("1", "2");
        assertEquals(1, user1.getFriendsIds().size()); // Verifica user1 actualizado de nuevo
    }

    @Test
    public void testAddFriend_MissingUserId() {
        // Arrange: Solo existe el amigo (friend)
        User friend = User.builder().id("2").username("friend").build();
        when(userRepository.findByIdIn(List.of("1", "2"))).thenReturn(List.of(friend));

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userService.addFriend("1", "2"));

        assertEquals("User not found with 'id' : '1'", exception.getMessage());
    }

    @Test
    public void testAddFriend_MissingFriendId() {
        // Arrange: Solo existe el usuario principal
        User user = User.builder().id("1").username("user").build();
        when(userRepository.findByIdIn(List.of("1", "2"))).thenReturn(List.of(user));

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userService.addFriend("1", "2"));

        assertEquals("User not found with 'id' : '2'", exception.getMessage());
    }

    @Test
    public void testCreateUser_DuplicateUsername() {
        // Arrange
        CreateUserDTO dto = new CreateUserDTO(
                "1",
                "existingUser", // Username que ya existe
                "img.png",
                "typeA",
                "#FFF",
                "#000",
                "#F00");

        // Configurar mocks para que:
        when(userRepository.findById("1")).thenReturn(Optional.empty()); // ID no existe
        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(new User())); // Username existe

        // Act & Assert
        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
                () -> userService.createUser(dto));

        assertEquals("User", exception.getClassName());
        assertEquals("Username", exception.getAtributeName());
        assertEquals("existingUser", exception.getFieldName());
        verify(userRepository, never()).save(any()); // No debe guardar
    }

    // ==================== PRUEBAS PARA updateUserStats ====================
    @Test
    public void testUpdateUserStats_IncrementLostGames() {
        // Arrange - Usuario con valores iniciales
        User user = User.builder()
                .id("1")
                .totalScore(100)
                .bestScore(50)
                .lostGames(2)
                .gamesWon(3)
                .build();

        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        // Act - Actualizar con derrota (isWinner = false)
        UserResponseDTO result = userService.updateUserStats("1", 30, false);

        // Assert - Verificar cambios
        assertEquals(3, result.getGamesWon()); // No debe cambiar
        assertEquals(3, result.getLostGames()); // 2 + 1 = 3
        assertEquals(130, result.getTotalScore()); // 100 + 30
        assertEquals(50, result.getBestScore()); // 30 < 50, no cambia
    }

    @Test
    public void testUpdateUserStats_NewBestScoreOnLoss() {
        // Arrange - Usuario con bestScore bajo
        User user = User.builder()
                .id("1")
                .totalScore(50)
                .bestScore(20)
                .lostGames(1)
                .build();

        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        // Act - Pierde pero con puntaje alto
        UserResponseDTO result = userService.updateUserStats("1", 100, false);

        // Assert
        assertEquals(150, result.getTotalScore()); // 50 + 100
        assertEquals(100, result.getBestScore()); // Nuevo mejor puntaje
        assertEquals(2, result.getLostGames());
    }

    @Test
    public void testUpdateStats_PlayerNotFoundInRequest() {
        // Arrange - Stat con playerId que no existe en la lista de players
        UpdateStatsRequest request = new UpdateStatsRequest();
        request.setStats(List.of(
                new UpdateStatsRequest.Stat("nonExistentPlayerId", "GOAL_SCORED")));
        request.setPlayers(List.of(
                new UpdateStatsRequest.PlayerDTO("player1", "Jugador1", "session1", 0, 0, 0)));

        // Act
        String result = userService.updateStats(request);

        // Assert - No debe haber interacción con el repositorio
        verify(userRepository, never()).findById(any());
        verify(userRepository, never()).save(any());
        assertEquals("statistics update successful", result);
    }

    @Test
    public void testUpdateStats_UserNotInDatabase() {
        // Arrange - Player existe en el request pero no en la base de datos
        UpdateStatsRequest request = new UpdateStatsRequest();
        String missingPlayerId = "player2";
        request.setStats(List.of(
                new UpdateStatsRequest.Stat(missingPlayerId, "GOAL_SCORED")));
        request.setPlayers(List.of(
                new UpdateStatsRequest.PlayerDTO(missingPlayerId, "Jugador2", "session1", 1, 0, 0)));

        when(userRepository.findById(missingPlayerId)).thenReturn(Optional.empty());

        // Act
        String result = userService.updateStats(request);

        // Assert - No se guardan cambios
        verify(userRepository, never()).save(any());
        assertEquals("statistics update successful", result);
    }

    @Test
    public void testUpdateStats_Team1Wins() {
        // Arrange
        // 1. Crear User (sin team)
        User team1Player = User.builder()
                .id("player1")
                .gamesWon(0)
                .lostGames(0)
                .totalScore(0)
                .bestScore(0)
                .build();

        // 2. PlayerDTO define el equipo
        UpdateStatsRequest.PlayerDTO player1DTO = new UpdateStatsRequest.PlayerDTO(
                "player1",
                "Team1Player",
                "session1",
                1, // Team 1
                0,
                0);

        // 3. Configurar request con stats
        UpdateStatsRequest request = new UpdateStatsRequest();
        request.setStats(List.of(
                new UpdateStatsRequest.Stat("player1", "GOAL_SCORED"),
                new UpdateStatsRequest.Stat("player1", "GOAL_SCORED")));
        request.setPlayers(List.of(player1DTO));

        // 4. Mock del repositorio
        when(userRepository.findById("player1")).thenReturn(Optional.of(team1Player));
        when(userRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        // Act
        String result = userService.updateStats(request);

        // Assert
        assertEquals(40, team1Player.getTotalScore()); // 2 goles * 20 puntos
        assertEquals(20, team1Player.getBestScore());
        assertEquals("statistics update successful", result);

        // Verificar que team1 ganó (se actualizan sus estadísticas)
        assertEquals(1, team1Player.getGamesWon());
        assertEquals(0, team1Player.getLostGames());
    }

    // ==================== PRUEBAS PARA updateGameStats ====================
    @Test
    public void testUpdateGameStats_IncrementLostGames() throws Exception {
        // 1. Configurar datos de prueba
        UpdateStatsRequest request = new UpdateStatsRequest();
        request.setPlayers(List.of(
                new UpdateStatsRequest.PlayerDTO("player1", "Jugador1", "session1", 0, 0, 0)));

        User user = User.builder()
                .id("player1")
                .lostGames(2)
                .build();

        // 2. Mockear el repositorio
        when(userRepository.findById("player1")).thenReturn(Optional.of(user));

        // 3. Acceder al método privado usando Reflection
        Method method = UserServiceImp.class.getDeclaredMethod(
                "updateGameStats",
                UpdateStatsRequest.class,
                int.class,
                boolean.class);
        method.setAccessible(true); // Hacer el método accesible

        // 4. Ejecutar el método
        method.invoke(userService, request, 0, false);

        // 5. Verificar resultados
        assertEquals(3, user.getLostGames(), "Los lostGames deben incrementarse en 1");
        verify(userRepository).saveAll(List.of(user));
    }

    // ==================== PRUEBAS PARA getAllUserSummaries ====================
    @Test
    public void testGetAllUserSummaries_Success() {
        // Arrange
        List<String> ids = List.of("1", "2");
        List<UserSummaryDTO> mockSummaries = List.of(
                UserSummaryDTO.builder()
                        .id("1")
                        .username("user1")
                        .gamesWon(5)
                        .lostGames("3") // Notar el tipo String
                        .build(),
                UserSummaryDTO.builder()
                        .id("2")
                        .username("user2")
                        .gamesWon(2)
                        .lostGames("1")
                        .build());

        when(userRepository.findAllUserSummaries(ids)).thenReturn(mockSummaries);

        // Act
        List<UserSummaryDTO> result = userService.getAllUserSummaries(ids);

        // Assert
        assertEquals(2, result.size());
        assertEquals("3", result.get(0).getLostGames());
        verify(userRepository).findAllUserSummaries(ids);
    }

    @Test
    public void testGetAllUserSummaries_EmptyList() {
        when(userRepository.findAllUserSummaries(anyList())).thenReturn(Collections.emptyList());

        List<UserSummaryDTO> result = userService.getAllUserSummaries(List.of("99"));

        assertTrue(result.isEmpty());
    }
    // @Test
    // public void testFindPotentialFriends_SortDescending() {
    // // Arrange
    // String currentUserId = "1";
    // String searchTerm = "user";
    // int pageNumber = 0;
    // int pageSize = 10;
    // String sortBy = "username";
    // String sortDir = "desc";

    // // Configurar usuario actual y amigos
    // User currentUser = User.builder()
    // .id(currentUserId)
    // .friendsIds(Set.of("2"))
    // .build();

    // Set<String> excludedIds = new HashSet<>(currentUser.getFriendsIds());
    // excludedIds.add(currentUserId); // IDs excluidos: ["1", "2"]

    // // Mock de usuarios ordenados DESC por username
    // List<User> mockUsers = Arrays.asList(
    // User.builder().id("3").username("userC").build(),
    // User.builder().id("4").username("userB").build(),
    // User.builder().id("5").username("userA").build()
    // );

    // Page<User> usersPage = new PageImpl<>(
    // mockUsers,
    // PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending()),
    // mockUsers.size()
    // );

    // // Configurar mocks
    // when(userRepository.findById(currentUserId)).thenReturn(Optional.of(currentUser));
    // when(userRepository.findByUsernameContainingIgnoreCaseAndIdNotIn(
    // searchTerm.trim(),
    // excludedIds,
    // PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending())
    // )).thenReturn(usersPage);

    // // Act
    // UsersResponse response = userService.findPotentialFriends(
    // currentUserId, searchTerm, pageNumber, pageSize, sortBy, sortDir
    // );

    // // Assert - Verificar orden DESC
    // assertEquals(3, response.getUsers().size());
    // assertEquals("userC", response.getUsers().get(0).getUsername()); // Primero
    // en DESC
    // assertEquals("userA", response.getUsers().get(2).getUsername());
    // assertEquals(sortDir.toUpperCase(), response.getSortDir());
    // }

    @Test
    public void testFindPotentialFriends_EmptySearchTermUsesFindByIdNotIn() {
        // Arrange
        String currentUserId = "1";
        String searchTerm = "";
        int pageNumber = 0;
        int pageSize = 10;
        String sortBy = "username";
        String sortDir = "asc";

        // Configurar usuario actual y amigos
        User currentUser = User.builder()
                .id(currentUserId)
                .friendsIds(Set.of("2"))
                .build();

        Set<String> excludedIds = new HashSet<>(currentUser.getFriendsIds());
        excludedIds.add(currentUserId); // IDs excluidos: ["1", "2"]

        // Mock de usuarios sin término de búsqueda
        Page<User> usersPage = new PageImpl<>(List.of(
                User.builder().id("3").username("user3").build()));

        // Configurar mocks
        when(userRepository.findById(currentUserId)).thenReturn(Optional.of(currentUser));
        when(userRepository.findByIdNotIn(excludedIds,
                PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending())))
                .thenReturn(usersPage);

        // Act
        UsersResponse response = userService.findPotentialFriends(
                currentUserId, searchTerm, pageNumber, pageSize, sortBy, sortDir);

        // Assert - Usar ArgumentCaptor para capturar parámetros
        ArgumentCaptor<Set<String>> idsCaptor = ArgumentCaptor.forClass(Set.class);
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        verify(userRepository).findByIdNotIn(idsCaptor.capture(), pageableCaptor.capture());

        // Verificar IDs excluidos
        Set<String> capturedIds = idsCaptor.getValue();
        assertEquals(2, capturedIds.size());
        assertTrue(capturedIds.containsAll(Set.of("1", "2")));

        // Verificar Pageable
        PageRequest capturedPageable = (PageRequest) pageableCaptor.getValue();
        assertEquals(pageNumber, capturedPageable.getPageNumber());
        assertEquals(pageSize, capturedPageable.getPageSize());
        assertEquals(Sort.Direction.ASC, capturedPageable.getSort().getOrderFor("username").getDirection());

        // Verificar respuesta
        assertEquals(1, response.getUsers().size());
        assertEquals("user3", response.getUsers().get(0).getUsername());
    }

// @Test
// public void testRemoveFriend_Success() {
//     // Arrange
//     String userId = "1";
//     String friendId = "2";
    
//     User user = User.builder()
//             .id(userId)
//             .friendsIds(new HashSet<>(Set.of(friendId))) // Tienen amistad
//             .build();
            
//     User friend = User.builder()
//             .id(friendId)
//             .friendsIds(new HashSet<>(Set.of(userId))) // Amistad recíproca
//             .build();

//     // Configurar mocks
//     when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//     when(userRepository.findById(friendId)).thenReturn(Optional.of(friend));
//     when(userRepository.save(user)).thenReturn(user);
//     when(userRepository.save(friend)).thenReturn(friend);

//     // Act
//     FriendResponseDTO response = userService.removeFriend(userId, friendId);

//     // Assert
//     assertEquals("REMOVED", response.getOperation());
//     assertEquals(userId, response.getUserId());
//     assertEquals(friendId, response.getFriendId());
//     assertEquals(user.getFriendsIds().size(), response.getCurrentFriendsCount());
    
//     // Verificar que se eliminó la amistad en ambos usuarios
//     assertFalse(user.getFriendsIds().contains(friendId), "El amigo debería ser removido de la lista de user");
//     assertFalse(friend.getFriendsIds().contains(userId), "El user debería ser removido de la lista de friend");
    
//     verify(userRepository).save(user);
//     verify(userRepository).save(friend);
// }

@Test
public void testRemoveFriend_VerifyFriendRemovalFromBothSides() {
    // Arrange
    User user = User.builder()
            .id("1")
            .friendsIds(new HashSet<>(Set.of("2"))) // Amigo 2
            .build();
            
    User friend = User.builder()
            .id("2")
            .friendsIds(new HashSet<>(Set.of("1"))) // Amigo 1
            .build();

    when(userRepository.findById("1")).thenReturn(Optional.of(user));
    when(userRepository.findById("2")).thenReturn(Optional.of(friend));
    when(userRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

    // Act
    userService.removeFriend("1", "2");

    // Assert
    assertFalse(user.getFriendsIds().contains("2"), "El amigo debería ser removido de la lista de user");
    assertFalse(friend.getFriendsIds().contains("1"), "El user debería ser removido de la lista de friend");
}

@Test
public void testRemoveFriend_ReturnsCorrectFriendCount() {
    // Arrange - Usuario con 2 amigos, elimina 1
    User user = User.builder()
            .id("1")
            .friendsIds(new HashSet<>(Set.of("2", "3"))) // Dos amigos
            .build();
            
    User friend = User.builder()
            .id("2")
            .friendsIds(new HashSet<>(Set.of("1")))
            .build();

    when(userRepository.findById("1")).thenReturn(Optional.of(user));
    when(userRepository.findById("2")).thenReturn(Optional.of(friend));
    when(userRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

    // Act
    FriendResponseDTO response = userService.removeFriend("1", "2");

    // Assert
    assertEquals(1, user.getFriendsIds().size());
    assertEquals("REMOVED", response.getOperation());
}

@Test
public void testRemoveFriend_SavesBothUsers() {
    // Arrange
    User user = User.builder().id("1").friendsIds(new HashSet<>(Set.of("2"))).build();
    User friend = User.builder().id("2").friendsIds(new HashSet<>(Set.of("1"))).build();

    when(userRepository.findById("1")).thenReturn(Optional.of(user));
    when(userRepository.findById("2")).thenReturn(Optional.of(friend));
    when(userRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

    // Act
    userService.removeFriend("1", "2");

    // Assert - Verifica que se guardaron ambos usuarios
    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
    verify(userRepository, times(2)).save(userCaptor.capture());
    
    List<User> savedUsers = userCaptor.getAllValues();
    assertEquals(2, savedUsers.size());
    assertTrue(savedUsers.contains(user));
    assertTrue(savedUsers.contains(friend));
}
@Test
public void testFindPotentialFriends_SortDescending() {
    // Arrange
    String currentUserId = "1";
    String searchTerm = "user";
    int pageNumber = 0;
    int pageSize = 10;
    String sortBy = "username";
    String sortDir = "desc";  // Orden descendente

    // Configurar usuario actual y amigos
    User currentUser = User.builder()
            .id(currentUserId)
            .friendsIds(Set.of("2"))  // Amigo excluido
            .build();

    // Mock de usuarios ordenados DESC por username
    List<User> mockUsers = Arrays.asList(
            User.builder().id("3").username("userC").build(),
            User.builder().id("4").username("userB").build(),
            User.builder().id("5").username("userA").build()
    );

    Page<User> usersPage = new PageImpl<>(
            mockUsers,
            PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending()),
            mockUsers.size()
    );

    // Configurar mocks
    when(userRepository.findById(currentUserId)).thenReturn(Optional.of(currentUser));
    when(userRepository.findByUsernameContainingIgnoreCaseAndIdNotIn(
            eq(searchTerm), 
            anySet(), 
            any(Pageable.class)))
            .thenReturn(usersPage);

    // Act
    UsersResponse response = userService.findPotentialFriends(
            currentUserId, searchTerm, pageNumber, pageSize, sortBy, sortDir);

    // Assert - Verificar orden DESC
    assertEquals(3, response.getUsers().size());
    
    // Verificar orden descendente por username
    assertEquals("userC", response.getUsers().get(0).getUsername());  // Primero en DESC
    assertEquals("userB", response.getUsers().get(1).getUsername());
    assertEquals("userA", response.getUsers().get(2).getUsername());  // Último en DESC
    
    // Verificar parámetros de paginación
    assertEquals(pageNumber, response.getPagesNo());
    assertEquals(pageSize, response.getPageSize());
    assertEquals(1, response.getTotalPages());
}

@Test
public void testFindPotentialFriends_SortDescending_VerifyPageable() {
    // Arrange
    String currentUserId = "1";
    String sortBy = "username";
    String sortDir = "desc";
    
    User currentUser = User.builder().id(currentUserId).build();
    Page<User> emptyPage = new PageImpl<>(Collections.emptyList());
    
    when(userRepository.findById(currentUserId)).thenReturn(Optional.of(currentUser));
    when(userRepository.findByIdNotIn(anySet(), any(Pageable.class))).thenReturn(emptyPage);

    // Act
    userService.findPotentialFriends(currentUserId, null, 0, 10, sortBy, sortDir);

    // Assert - Verificar que se creó el PageRequest con sort descendente
    ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
    verify(userRepository).findByIdNotIn(anySet(), pageableCaptor.capture());
    
    PageRequest pageRequest = (PageRequest) pageableCaptor.getValue();
    Sort.Order order = pageRequest.getSort().getOrderFor("username");
    
    assertNotNull(order, "Debe tener ordenamiento por username");
    assertEquals(Sort.Direction.DESC, order.getDirection(), "Debe ser orden descendente");
}
}