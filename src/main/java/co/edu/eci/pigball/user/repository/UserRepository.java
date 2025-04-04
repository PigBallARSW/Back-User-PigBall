package co.edu.eci.pigball.user.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import co.edu.eci.pigball.user.model.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
