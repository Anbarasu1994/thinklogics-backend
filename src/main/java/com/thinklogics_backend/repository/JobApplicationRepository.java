package com.thinklogics_backend.repository;

import com.thinklogics_backend.model.JobApplication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JobApplicationRepository extends MongoRepository<JobApplication,String> {

}


