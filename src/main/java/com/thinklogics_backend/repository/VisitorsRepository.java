package com.thinklogics_backend.repository;

import com.thinklogics_backend.model.Visitors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitorsRepository extends JpaRepository <Visitors,Long>{
    Optional<Visitors> findByEmail(String email);

    boolean existsByEmail(String email);
}
