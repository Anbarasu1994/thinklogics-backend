package com.thinklogics_backend.repository;

import com.thinklogics_backend.model.SessionAttendee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SessionAttendeeRepository extends MongoRepository<SessionAttendee,String> {
    boolean existsByPhoneNumber(String phoneNumber);
}
