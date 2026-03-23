package org.example.backend.repository;

import org.example.backend.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findUserByUsername(String username);
    void deleteUserByUsername(String username);
    List<User> findUsersByUsernameNotIn (List<String> usernames);
}
