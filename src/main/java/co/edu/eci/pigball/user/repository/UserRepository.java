package co.edu.eci.pigball.user.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import co.edu.eci.pigball.user.dto.UserSummaryDTO;
import co.edu.eci.pigball.user.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends MongoRepository<User, String> {
    
    Optional<User> findByUsername(String username);

    @Query(
        value = "{ '_id': { '$in': ?0 } }",
        fields = "{ '_id': 1, 'username': 1, 'gamesWon': 1, 'lostGames': 1, 'image': 1, 'iconType': 1, 'borderColor': 1, 'centerColor': 1, 'iconColor': 1 }"
    )
    List<UserSummaryDTO> findAllUserSummaries(List<String> ids);
    List<User> findByIdIn(List<String> ids);
    Page<User> findByUsernameContainingIgnoreCaseAndIdNotIn(
            String username, Set<String> excludedIds, Pageable pageable);

    Page<User> findByIdNotIn(Set<String> excludedIds, Pageable pageable);
}
