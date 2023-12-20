package com.thinklogics_backend.repository;

import com.thinklogics_backend.model.JobDescription;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobDescriptionRepository extends MongoRepository<JobDescription,String> {
}
