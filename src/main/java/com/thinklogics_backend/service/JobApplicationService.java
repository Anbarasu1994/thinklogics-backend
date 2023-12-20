package com.thinklogics_backend.service;

import com.thinklogics_backend.model.JobApplication;
import com.thinklogics_backend.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class JobApplicationService {
    private final JobApplicationRepository jobApplicationRepository;

    @Autowired
    public JobApplicationService(JobApplicationRepository jobApplicationRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
    }

    public List<JobApplication> getAllJobApplications() {
        return jobApplicationRepository.findAll();
    }

    public Optional<JobApplication> getJobApplicationById(String id) {
        return jobApplicationRepository.findById(id);
    }

    public JobApplication createJobApplication(JobApplication jobApplication) {
        return jobApplicationRepository.save(jobApplication);
    }

    public JobApplication updateJobApplication(String id, JobApplication updatedJobApplication, MultipartFile resume) {
        Optional<JobApplication> existingJobApplication = jobApplicationRepository.findById(id);
        if (existingJobApplication.isPresent()) {
            JobApplication jobApp = existingJobApplication.get();
            jobApp.setName(updatedJobApplication.getName());
            jobApp.setEmail(updatedJobApplication.getEmail());
            jobApp.setCoverLetter(updatedJobApplication.getCoverLetter());
            try {
                byte[] resumeData = resume.getBytes();
                jobApp.setResume(resumeData);
            } catch (IOException e) {
                throw new RuntimeException("Error converting resume to byte array");
            }
            jobApp.setJobTitle(updatedJobApplication.getJobTitle());
            return jobApplicationRepository.save(jobApp);
        } else {
            throw new IllegalArgumentException("Job Application not found with ID: " + id);
        }
    }

    public void deleteJobApplication(String id) {
        jobApplicationRepository.deleteById(id);
    }
}
