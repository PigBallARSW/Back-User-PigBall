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

        // Pruebas adicionales para equals() y hashCode()
        @Test
        void testEquals_SameInstance_ReturnsTrue() {
                UsersResponse response = UsersResponse.builder().pagesNo(1).build();
                assertEquals(response, response);
        }

        @Test
        void testEquals_NullComparison_ReturnsFalse() {
                UsersResponse response = UsersResponse.builder().totalElements(10L).build();
                assertNotEquals(null, response);
        }

        @Test
        void testEquals_DifferentClass_ReturnsFalse() {
                UsersResponse response = UsersResponse.builder().build();
                assertNotEquals( "Not a UsersResponse object",response);
        }

        @Test
        void testEquals_DifferentUsersList_NotEqual() {
                List<UserResponseDTO> users1 = List.of(new UserResponseDTO());
                List<UserResponseDTO> users2 = List.of(new UserResponseDTO(), new UserResponseDTO());

                UsersResponse res1 = UsersResponse.builder().users(users1).build();
                UsersResponse res2 = UsersResponse.builder().users(users2).build();

                assertNotEquals(res1, res2);
        }

        @Test
        void testEquals_DifferentPagesNo_NotEqual() {
                UsersResponse res1 = UsersResponse.builder().pagesNo(1).build();
                UsersResponse res2 = UsersResponse.builder().pagesNo(2).build();
                assertNotEquals(res1, res2);
        }

        @Test
        void testEquals_DifferentPageSize_NotEqual() {
                UsersResponse res1 = UsersResponse.builder().pageSize(10).build();
                UsersResponse res2 = UsersResponse.builder().pageSize(20).build();
                assertNotEquals(res1, res2);
        }

        @Test
        void testEquals_DifferentTotalPages_NotEqual() {
                UsersResponse res1 = UsersResponse.builder().totalPages(3).build();
                UsersResponse res2 = UsersResponse.builder().totalPages(5).build();
                assertNotEquals(res1, res2);
        }

        @Test
        void testEquals_DifferentLastOne_NotEqual() {
                UsersResponse res1 = UsersResponse.builder().lastOne(true).build();
                UsersResponse res2 = UsersResponse.builder().lastOne(false).build();
                assertNotEquals(res1, res2);
        }

        @Test
        void testEquals_NullVsTrueLastOne_NotEqual() {
                UsersResponse res1 = UsersResponse.builder().lastOne(null).build();
                UsersResponse res2 = UsersResponse.builder().lastOne(true).build();
                assertNotEquals(res1, res2);
        }

        @Test
        void testEquals_DifferentTotalElements_NotEqual() {
                UsersResponse res1 = UsersResponse.builder().totalElements(100L).build();
                UsersResponse res2 = UsersResponse.builder().totalElements(200L).build();
                assertNotEquals(res1, res2);
        }

        @Test
        void testHashCode_WithNullFields_DoesNotThrow() {
                UsersResponse response = UsersResponse.builder()
                                .users(null)
                                .lastOne(null)
                                .totalElements(null)
                                .build();

                assertDoesNotThrow(response::hashCode);
        }

        @Test
        void testHashCode_DifferentUsers_DifferentHash() {
                List<UserResponseDTO> users1 = List.of(new UserResponseDTO());
                List<UserResponseDTO> users2 = List.of(new UserResponseDTO());
                users2.get(0).setId("different");

                UsersResponse res1 = UsersResponse.builder().users(users1).build();
                UsersResponse res2 = UsersResponse.builder().users(users2).build();

                assertNotEquals(res1.hashCode(), res2.hashCode());
        }

        // Prueba para el toString() del builder
        @Test
        void testBuilderToString() {
                UsersResponse.UsersResponseBuilder builder = UsersResponse.builder()
                                .pagesNo(2)
                                .totalElements(50L)
                                .lastOne(true);

                String builderStr = builder.toString();

                assertAll(
                                () -> assertTrue(builderStr.contains("pagesNo=2")),
                                () -> assertTrue(builderStr.contains("totalElements=50")),
                                () -> assertTrue(builderStr.contains("lastOne=true")));
        }

        // Prueba para igualdad con mismos valores en diferentes instancias
        @Test
        void testEquals_SameValuesDifferentInstances_Equal() {
                UsersResponse res1 = UsersResponse.builder()
                                .pagesNo(1)
                                .pageSize(10)
                                .totalPages(2)
                                .build();

                UsersResponse res2 = UsersResponse.builder()
                                .pagesNo(1)
                                .pageSize(10)
                                .totalPages(2)
                                .build();

                assertEquals(res1, res2);
                assertEquals(res1.hashCode(), res2.hashCode());
        }
}
