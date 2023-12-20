package com.thinklogics_backend.repository;

import com.thinklogics_backend.model.SessionAttendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SessionAttendeeRepository extends JpaRepository<SessionAttendee,Long> {
    boolean existsByPhoneNumber(String phoneNumber);
}
