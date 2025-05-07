package co.edu.eci.pigball.user.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersResponse {
    private List<UserResponseDTO> users;
    private int pagesNo;         // El número de página actual
    private int pageSize;        // Cuántos elementos por página
    private int totalPages;      // Cuántas páginas hay en total
    private Boolean lastOne;     // Si es la última página (true/false)
    private Long totalElements;  // Total de usuarios encontrados
}