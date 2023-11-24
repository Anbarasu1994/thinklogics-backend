    package com.thinklogics_backend.controller;

    import com.thinklogics_backend.model.JobApplication;
    import com.thinklogics_backend.service.JobApplicationService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.IOException;
    import java.util.List;
    import java.util.Optional;

    @RestController
    @CrossOrigin("*")
    @RequestMapping("/jobApplications")
    public class JobApplicationController {
        private final JobApplicationService jobApplicationService;

        @Autowired
        public JobApplicationController(JobApplicationService jobApplicationService) {
            this.jobApplicationService = jobApplicationService;
        }

        @GetMapping
        public List<JobApplication> getAllJobApplications() {
            return jobApplicationService.getAllJobApplications();
        }

        @GetMapping("/{id}")
        public ResponseEntity<JobApplication> getJobApplicationById(@PathVariable Long id) {
            Optional<JobApplication> jobApplication = jobApplicationService.getJobApplicationById(id);
            return jobApplication.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }
        @PostMapping
        public ResponseEntity<JobApplication> createJobApplication(
                @RequestParam("name") String name,
                @RequestParam("email") String email,
                @RequestParam("coverLetter") String coverLetter,
                @RequestParam("jobTitle") String jobTitle,
                @RequestParam("resume") MultipartFile resume
        ) {
            // Create a JobApplication instance and set its properties
            JobApplication jobApplication = new JobApplication();
            jobApplication.setName(name);
            jobApplication.setEmail(email);
            jobApplication.setCoverLetter(coverLetter);
            jobApplication.setJobTitle(jobTitle);

            try {
                // Convert and set the resume as a byte array
                jobApplication.setResume(resume.getBytes());
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            // Call the service to create the job application
            JobApplication createdJobApplication = jobApplicationService.createJobApplication(jobApplication);

            return ResponseEntity.ok(createdJobApplication);
        }



        @PutMapping("/{id}")
        public ResponseEntity<JobApplication> updateJobApplication(
                @PathVariable Long id,
                @RequestParam("resume") MultipartFile resume,
                @ModelAttribute JobApplication updatedJobApplication
        ) {
            JobApplication jobApplication = jobApplicationService.updateJobApplication(id, updatedJobApplication, resume);
            return ResponseEntity.ok(jobApplication);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteJobApplication(@PathVariable Long id) {
            jobApplicationService.deleteJobApplication(id);
            return ResponseEntity.noContent().build();
        }
    }
