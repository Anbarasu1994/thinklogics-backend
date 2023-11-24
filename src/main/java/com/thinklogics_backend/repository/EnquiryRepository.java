package com.thinklogics_backend.repository;

import com.thinklogics_backend.model.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnquiryRepository extends JpaRepository<Enquiry,Long> {
}
