package co.edu.eci.pigball.TestController;

import co.edu.eci.pigball.user.UserApplication;
import co.edu.eci.pigball.user.controller.UserController;
import co.edu.eci.pigball.user.dto.*;
import co.edu.eci.pigball.user.model.request.UpdateStatsRequest;
import co.edu.eci.pigball.user.model.request.UpdateStatsRequest.PlayerDTO;
import co.edu.eci.pigball.user.model.request.UpdateStatsRequest.Stat;
import co.edu.eci.pigball.user.service.UserServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = UserApplication.class)
@TestPropertySource(properties = "server.ssl.enabled=false")
public class UserControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private UserServiceImp userService;

        @Autowired
        private ObjectMapper objectMapper;

        private UserResponseDTO sampleUser;
        private UserSummaryDTO sampleSummary;

        @BeforeEach
        public void setUp() {
                sampleUser = UserResponseDTO.builder()
                                .id("1")
                                .username("jag")
                                .image("img.png")
                                .iconType("typeA")
                                .borderColor("#FFFFFF")
                                .centerColor("#000000")
                                .iconColor("#FF0000")
                                .totalScore(0)
                                .gamesWon(0)
                                .build();

                sampleSummary = UserSummaryDTO.builder()
                                .id("1")
                                .username("jag")
                                .gamesWon(5)
                                .image("img.png")
                                .iconType("typeA")
                                .borderColor("#FFFFFF")
                                .centerColor("#000000")
                                .iconColor("#FF0000")
                                .build();
        }

        @Test
        public void testCreateUser() throws Exception {
                CreateUserDTO dto = CreateUserDTO.builder()
                                .id("1")
                                .username("jag")
                                .image("img.png")
                                .iconType("typeA")
                                .borderColor("#FFFFFF")
                                .centerColor("#000000")
                                .iconColor("#FF0000")
                                .build();

                Mockito.when(userService.createUser(any(CreateUserDTO.class))).thenReturn(sampleUser);

                mockMvc.perform(post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").value("1"))
                                .andExpect(jsonPath("$.username").value("jag"));
        }

        @Test
        public void testGetUserById() throws Exception {
                Mockito.when(userService.getUserById("1")).thenReturn(sampleUser);

                mockMvc.perform(get("/user/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.image").value("img.png"));
        }

        @Test
        public void testGetAllUsers() throws Exception {
                Mockito.when(userService.getAllUsers()).thenReturn(Collections.singletonList(sampleUser));

                mockMvc.perform(get("/user"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("[0].id").value("1"));
        }

        @Test
        public void testUpdateUserStats() throws Exception {
                Mockito.when(userService.updateUserStats(eq("1"), eq(100), eq(true))).thenReturn(sampleUser);

                mockMvc.perform(put("/user/stats/1")
                                .param("score", "100")
                                .param("isWinner", "true"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value("1"));
        }

        @Test
        public void testUpdateUser() throws Exception {
                UpdateUserDTO dto = UpdateUserDTO.builder()
                                .username("newName")
                                .image("new.png")
                                .borderColor("#AAAAAA")
                                .centerColor("#BBBBBB")
                                .iconColor("#CCCCCC")
                                .iconType("typeB")
                                .build();

                Mockito.when(userService.updateUser(eq("1"), any(UpdateUserDTO.class))).thenReturn(sampleUser);

                mockMvc.perform(put("/user/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.iconType").value("typeA"));
        }

        @Test
        public void testDeleteUser() throws Exception {
                Mockito.doNothing().when(userService).deleteUser("1");

                mockMvc.perform(delete("/user/1"))
                                .andExpect(status().isNoContent());
        }

        @Test
        public void testGetUserSummaries() throws Exception {
                Mockito.when(userService.getAllUserSummaries(anyList()))
                                .thenReturn(Collections.singletonList(sampleSummary));

                mockMvc.perform(post("/user/summary")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(Arrays.asList("1", "2"))))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("[0].gamesWon").value(5));
        }

        @Test
        public void testGetPotentialFriends() throws Exception {
                UsersResponse resp = UsersResponse.builder()
                                .users(Collections.emptyList())
                                .pagesNo(0)
                                .pageSize(10)
                                .totalPages(0)
                                .lastOne(true)
                                .totalElements(0L)
                                .build();

                Mockito.when(userService.findPotentialFriends(
                                anyString(),
                                anyString(),
                                anyInt(),
                                anyInt(),
                                anyString(),
                                anyString())).thenReturn(resp);

                mockMvc.perform(get("/user/potential-friends/1")
                                .param("search", "test")
                                .param("pageNo", "0")
                                .param("pageSize", "10")
                                .param("sortBy", "username")
                                .param("sortDir", "asc"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.users").isArray());
        }

        @Test
        public void testAddFriend() throws Exception {
                FriendResponseDTO added = FriendResponseDTO.added("1", "2", 1);
                Mockito.when(userService.addFriend("1", "2")).thenReturn(added);

                mockMvc.perform(post("/user/1/friends/2"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.operation").value("ADDED"));
        }

        @Test
        public void testRemoveFriend() throws Exception {
                FriendResponseDTO removed = FriendResponseDTO.removed("1", "2", 0);
                Mockito.when(userService.removeFriend("1", "2")).thenReturn(removed);

                mockMvc.perform(delete("/user/1/friends/2"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.operation").value("REMOVED"));
        }

        @Test
        public void testGetUserByUsername() throws Exception {
        Mockito.when(userService.getUserByUsername("jag")).thenReturn(sampleUser);

        mockMvc.perform(get("/user/username/jag"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("jag"))
                .andExpect(jsonPath("$.totalScore").value(0));
        }

        @Test
        public void testUpdateStats() throws Exception {
        // 1. Configurar el request
        UpdateStatsRequest request = new UpdateStatsRequest();
        request.setStats(List.of(
                new UpdateStatsRequest.Stat("player1", "GOAL_SCORED") // Ejemplo realista
        ));
        request.setPlayers(List.of(
                new UpdateStatsRequest.PlayerDTO("player1", "Jugador1", "session123", 0, 10.5, 20.3)
        ));

        // 2. Mockear el servicio para retornar el String esperado
        Mockito.when(userService.updateStats(Mockito.any(UpdateStatsRequest.class)))
                .thenReturn("statistics update successful");

        // 3. Ejecutar y validar
        mockMvc.perform(put("/user/stats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("statistics update successful")); // Validar el String de respuesta
        }

        @Test
        public void testGetFriends() throws Exception {
        // Configurar respuesta esperada
        UsersResponse response = UsersResponse.builder()
                .users(Collections.singletonList(sampleUser)) // Usar UserResponseDTO
                .pagesNo(0)
                .pageSize(10)
                .totalPages(1)
                .lastOne(true)
                .totalElements(1L)
                .build();

        Mockito.when(userService.getFriendsList("1")).thenReturn(response);

        mockMvc.perform(get("/user/1/friends"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users[0].username").value("jag")) // UserResponseDTO
                .andExpect(jsonPath("$.pagesNo").value(0))
                .andExpect(jsonPath("$.totalElements").value(1));
        }
}