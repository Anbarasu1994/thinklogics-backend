package com.thinklogics_backend.repository;

import com.thinklogics_backend.model.JobDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobDescriptionRepository extends JpaRepository<JobDescription,Long> {
}
