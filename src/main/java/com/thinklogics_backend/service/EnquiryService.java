package com.thinklogics_backend.service;

import com.thinklogics_backend.model.Enquiry;
import com.thinklogics_backend.repository.EnquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EnquiryService {

    private final EnquiryRepository enquiryRepository;

    @Autowired
    public EnquiryService(EnquiryRepository enquiryRepository) {
        this.enquiryRepository = enquiryRepository;
    }

    public List<Enquiry> getAllEnquiries() {
        return enquiryRepository.findAll();
    }

    public Optional<Enquiry> getEnquiryById(String id) {
        return enquiryRepository.findById(id);
    }

    public Enquiry createEnquiry(Enquiry enquiry) {
        return enquiryRepository.save(enquiry);
    }

    public Enquiry updateEnquiry(String id, Enquiry updatedEnquiry) {
        if (enquiryRepository.existsById(id)) {
            updatedEnquiry.setId(id);
            return enquiryRepository.save(updatedEnquiry);
        } else {
            throw new IllegalArgumentException("Enquiry not found with ID: " + id);
        }
    }

    public void deleteEnquiry(String id) {
        enquiryRepository.deleteById(id);
    }
}
