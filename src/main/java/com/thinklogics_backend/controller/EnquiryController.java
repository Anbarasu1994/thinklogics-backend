package com.thinklogics_backend.controller;

import com.thinklogics_backend.model.Enquiry;
import com.thinklogics_backend.service.EnquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import java.util.List;
import java.util.Optional;

@RestController
//@CrossOrigin(origins = "http://localhost:3000, http://127.0.0.1:5501")

@CrossOrigin("*")
@RequestMapping("/enquiries")
public class EnquiryController {

    private final EnquiryService enquiryService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    public EnquiryController(EnquiryService enquiryService) {
        this.enquiryService = enquiryService;
    }

    @GetMapping
    public List<Enquiry> getAllEnquiries() {
        return enquiryService.getAllEnquiries();
    }

    @GetMapping("/{id}")
    public Enquiry getEnquiryById(@PathVariable String id) {
        Optional<Enquiry> enquiry = enquiryService.getEnquiryById(id);
        if (enquiry.isPresent()) {
            return enquiry.get();
        } else {
            throw new IllegalArgumentException("Enquiry not found with ID: " + id);
        }
    }

    @PostMapping
    public Enquiry createEnquiry(@RequestBody Enquiry enquiry) {
        messagingTemplate.convertAndSend("/topic/newEnquiries", enquiry);
        return enquiryService.createEnquiry(enquiry);
    }

    @PutMapping("/{id}")
    public Enquiry updateEnquiry(@PathVariable String id, @RequestBody Enquiry updatedEnquiry) {
        return enquiryService.updateEnquiry(id, updatedEnquiry);
    }

    @DeleteMapping("/{id}")
    public void deleteEnquiry(@PathVariable String id) {
        enquiryService.deleteEnquiry(id);
    }
}
