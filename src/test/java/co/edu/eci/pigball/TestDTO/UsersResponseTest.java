package co.edu.eci.pigball.TestDTO;

import org.junit.jupiter.api.Test;

import co.edu.eci.pigball.user.dto.UserResponseDTO;
import co.edu.eci.pigball.user.dto.UsersResponse;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UsersResponseTest {

    @Test
    void testBuilderAndGetters() {
        // Arrange
        List<UserResponseDTO> expectedUsers = List.of(
                new UserResponseDTO(),
                new UserResponseDTO());
        int expectedPagesNo = 2;
        int expectedPageSize = 10;
        int expectedTotalPages = 5;
        Boolean expectedLastOne = false;
        Long expectedTotalElements = 50L;

        // Act
        UsersResponse response = UsersResponse.builder()
                .users(expectedUsers)
                .pagesNo(expectedPagesNo)
                .pageSize(expectedPageSize)
                .totalPages(expectedTotalPages)
                .lastOne(expectedLastOne)
                .totalElements(expectedTotalElements)
                .build();

        // Assert
        assertAll(
                () -> assertEquals(expectedUsers, response.getUsers()),
                () -> assertEquals(expectedPagesNo, response.getPagesNo()),
                () -> assertEquals(expectedPageSize, response.getPageSize()),
                () -> assertEquals(expectedTotalPages, response.getTotalPages()),
                () -> assertEquals(expectedLastOne, response.getLastOne()),
                () -> assertEquals(expectedTotalElements, response.getTotalElements()));
    }

    @Test
    void testSetters() {
        // Arrange
        UsersResponse response = UsersResponse.builder().build();
        List<UserResponseDTO> expectedUsers = List.of(new UserResponseDTO());
        int expectedPagesNo = 1;
        int expectedPageSize = 5;
        int expectedTotalPages = 2;
        Boolean expectedLastOne = true;
        Long expectedTotalElements = 10L;

        // Act
        response.setUsers(expectedUsers);
        response.setPagesNo(expectedPagesNo);
        response.setPageSize(expectedPageSize);
        response.setTotalPages(expectedTotalPages);
        response.setLastOne(expectedLastOne);
        response.setTotalElements(expectedTotalElements);

        // Assert
        assertAll(
                () -> assertEquals(expectedUsers, response.getUsers()),
                () -> assertEquals(expectedPagesNo, response.getPagesNo()),
                () -> assertEquals(expectedPageSize, response.getPageSize()),
                () -> assertEquals(expectedTotalPages, response.getTotalPages()),
                () -> assertEquals(expectedLastOne, response.getLastOne()),
                () -> assertEquals(expectedTotalElements, response.getTotalElements()));
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        List<UserResponseDTO> users = List.of(new UserResponseDTO());

        UsersResponse response1 = UsersResponse.builder()
                .users(users)
                .pagesNo(1)
                .pageSize(10)
                .totalPages(2)
                .lastOne(false)
                .totalElements(20L)
                .build();

        UsersResponse response2 = UsersResponse.builder()
                .users(users)
                .pagesNo(1)
                .pageSize(10)
                .totalPages(2)
                .lastOne(false)
                .totalElements(20L)
                .build();

        UsersResponse differentResponse = UsersResponse.builder()
                .users(users)
                .pagesNo(2)
                .pageSize(20)
                .totalPages(3)
                .lastOne(true)
                .totalElements(30L)
                .build();

        // Assert
        assertAll(
                () -> assertEquals(response1, response2),
                () -> assertNotEquals(response1, differentResponse),
                () -> assertEquals(response1.hashCode(), response2.hashCode()),
                () -> assertNotEquals(response1.hashCode(), differentResponse.hashCode()));
    }

    @Test
    void testToString() {
        // Arrange
        UsersResponse response = UsersResponse.builder()
                .users(List.of(new UserResponseDTO()))
                .pagesNo(1)
                .pageSize(10)
                .totalPages(2)
                .lastOne(false)
                .totalElements(20L)
                .build();

        // Act
        String toStringResult = response.toString();

        // Assert
        assertAll(
                () -> assertTrue(toStringResult.contains("users=")),
                () -> assertTrue(toStringResult.contains("pagesNo=1")),
                () -> assertTrue(toStringResult.contains("pageSize=10")),
                () -> assertTrue(toStringResult.contains("totalPages=2")),
                () -> assertTrue(toStringResult.contains("lastOne=false")),
                () -> assertTrue(toStringResult.contains("totalElements=20")));
    }

    @Test
    void testEmptyBuilder() {
        // Act
        UsersResponse response = UsersResponse.builder().build();

        // Assert
        assertAll(
                () -> assertNull(response.getUsers()),
                () -> assertEquals(0, response.getPagesNo()),
                () -> assertEquals(0, response.getPageSize()),
                () -> assertEquals(0, response.getTotalPages()),
                () -> assertNull(response.getLastOne()),
                () -> assertNull(response.getTotalElements()));
    }
}
