package co.edu.eci.pigball.user.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import co.edu.eci.pigball.user.dto.UserSummaryDTO;
import co.edu.eci.pigball.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    @Query(
        value = "{}",
        fields = "{ '_id': 1, 'username': 1, 'gamesWon': 1, 'image': 1, 'iconType': 1, 'borderColor': 1, 'centerColor': 1, 'iconColor': 1 }"
    )
    List<UserSummaryDTO> findAllUserSummaries();
}
