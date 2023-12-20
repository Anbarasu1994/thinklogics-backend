package com.thinklogics_backend.repository;

import com.thinklogics_backend.model.Visitors;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitorsRepository extends MongoRepository<Visitors,String> {
    Optional<Visitors> findByEmail(String email);

    boolean existsByEmail(String email);
}
