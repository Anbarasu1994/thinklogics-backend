package com.thinklogics_backend.repository;

import com.thinklogics_backend.model.Session;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository  extends MongoRepository<Session,String> {
}
