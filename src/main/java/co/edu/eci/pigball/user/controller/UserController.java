package co.edu.eci.pigball.user.controller;

import co.edu.eci.pigball.user.dto.CreateUserDTO;
import co.edu.eci.pigball.user.dto.FriendResponseDTO;
import co.edu.eci.pigball.user.dto.UpdateUserDTO;
import co.edu.eci.pigball.user.dto.UserResponseDTO;
import co.edu.eci.pigball.user.dto.UserSummaryDTO;
import co.edu.eci.pigball.user.dto.UsersResponse;
import co.edu.eci.pigball.user.model.request.UpdateStatsRequest;
import co.edu.eci.pigball.user.service.UserServiceImp;
import co.edu.eci.pigball.user.utils.AppConstants;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = {
    "https://nice-forest-015f0871e.6.azurestaticapps.net",
    "https://front-pig-ball.vercel.app",
    "https://localhost:3000",
    "http://localhost:3000",
})
@RestController
@RequestMapping("/user")
public class UserController {
    
    private final UserServiceImp userService;

    public UserController(UserServiceImp userService) {
        this.userService = userService;
    }

    // Crear usuario
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody CreateUserDTO userDTO) {
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Obtener usuario por username
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    // Listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Actualizar estadísticas de un usuario
    @PutMapping("/stats/{id}")
    public ResponseEntity<UserResponseDTO> updateUserStats(@PathVariable String id, 
                                                   @RequestParam int score, 
                                                   @RequestParam boolean isWinner) {
        return ResponseEntity.ok(userService.updateUserStats(id, score, isWinner));
    }

    // Actualizar el nombre de los usuarios
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable String id,@Valid @RequestBody UpdateUserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }
    @PutMapping("/stats")
    public ResponseEntity<String> updateStats(@Valid @RequestBody UpdateStatsRequest statsRequest) {
        return ResponseEntity.ok(userService.updateStats(statsRequest));
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/summary")
    public List<UserSummaryDTO> getUserSummaries(@RequestBody List<String> ids) {
        return userService.getAllUserSummaries(ids);
    }

    @GetMapping("/potential-friends/{currentUserId}")
    public ResponseEntity<UsersResponse> getPotentialFriends(
            @PathVariable String currentUserId,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER_BY_DEFAULT) int pageNo,
            @RequestParam(defaultValue = AppConstants.SIZE_PAGE_BY_DEFAULT) int pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_BY_DEFAULT) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIRECTION_BY_DEFAULT) String sortDir
           ) {
        
        UsersResponse response = userService.findPotentialFriends(
                currentUserId, search, pageNo, pageSize, sortBy, sortDir);
                
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId}/friends")
    public ResponseEntity<UsersResponse> getFriends(@PathVariable String userId) {
        UsersResponse response = userService.getFriendsList(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<FriendResponseDTO> addFriend(@PathVariable String userId, @PathVariable String friendId) {
        return ResponseEntity.ok(userService.addFriend(userId, friendId));
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<FriendResponseDTO> removeFriend(@PathVariable String userId, @PathVariable String friendId) {
        return ResponseEntity.ok(userService.removeFriend(userId, friendId));

    }
}


