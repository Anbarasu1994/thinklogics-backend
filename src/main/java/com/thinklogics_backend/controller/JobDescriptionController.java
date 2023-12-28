package com.thinklogics_backend.controller;

import com.thinklogics_backend.model.JobDescription;
import com.thinklogics_backend.service.JobDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jobDescriptions")
public class JobDescriptionController {
    private final JobDescriptionService jobDescriptionService;

    @Autowired
    public JobDescriptionController(JobDescriptionService jobDescriptionService) {
        this.jobDescriptionService = jobDescriptionService;
    }

    @GetMapping("/getAll")
    public List<JobDescription> getAllJobDescriptions() {
        return jobDescriptionService.getAllJobDescriptions();
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<JobDescription> getJobDescriptionById(@PathVariable String id) {
        Optional<JobDescription> jobDescription = jobDescriptionService.getJobDescriptionById(id);
        return jobDescription.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public JobDescription createJobDescription(@RequestBody JobDescription jobDescription) {
        return jobDescriptionService.createJobDescription(jobDescription);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<JobDescription> updateJobDescription(@PathVariable String id, @RequestBody JobDescription updatedJobDescription) {
        JobDescription jobDescription = jobDescriptionService.updateJobDescription(id, updatedJobDescription);
        return ResponseEntity.ok(jobDescription);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteJobDescription(@PathVariable String id) {
        jobDescriptionService.deleteJobDescription(id);
        return ResponseEntity.noContent().build();
    }
}
