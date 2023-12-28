package com.thinklogics_backend.service;

import com.thinklogics_backend.model.JobDescription;
import com.thinklogics_backend.repository.JobDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobDescriptionService {
    private final JobDescriptionRepository jobDescriptionRepository;

    @Autowired
    public JobDescriptionService(JobDescriptionRepository jobDescriptionRepository) {
        this.jobDescriptionRepository = jobDescriptionRepository;
    }

    public List<JobDescription> getAllJobDescriptions() {
        return jobDescriptionRepository.findAll();
    }

    public Optional<JobDescription> getJobDescriptionById(String id) {
        return jobDescriptionRepository.findById(id);
    }

    public JobDescription createJobDescription(JobDescription jobDescription) {
        return jobDescriptionRepository.save(jobDescription);
    }

    public JobDescription updateJobDescription(String id, JobDescription updatedJobDescription) {
        Optional<JobDescription> existingJobDescription = jobDescriptionRepository.findById(id);
        if (existingJobDescription.isPresent()) {
            JobDescription jobDesc = existingJobDescription.get();
            jobDesc.setDate(updatedJobDescription.getDate());
            jobDesc.setMonth(updatedJobDescription.getMonth());
            jobDesc.setJobTitle(updatedJobDescription.getJobTitle());
            jobDesc.setResponsibilities(updatedJobDescription.getResponsibilities());
            jobDesc.setRequirements(updatedJobDescription.getRequirements());
            return jobDescriptionRepository.save(jobDesc);
        } else {
            throw new IllegalArgumentException("Job Description not found with ID: " + id);
        }
    }

    public void deleteJobDescription(String id) {
        jobDescriptionRepository.deleteById(id);
    }
}
