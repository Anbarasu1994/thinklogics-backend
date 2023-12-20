package com.thinklogics_backend.repository;

import com.thinklogics_backend.model.Enquiry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnquiryRepository extends MongoRepository<Enquiry,String> {
}
