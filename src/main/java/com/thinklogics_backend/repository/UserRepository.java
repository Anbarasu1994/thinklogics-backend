package com.thinklogics_backend.repository;

import com.thinklogics_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);


    Optional<User> findByUserId(long userId);

    boolean existsByEmail(String email);
}
