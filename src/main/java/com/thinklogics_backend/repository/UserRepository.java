package com.thinklogics_backend.repository;

import com.thinklogics_backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);


    Optional<User> findByUserId(String userId);

    boolean existsByEmail(String email);
}
