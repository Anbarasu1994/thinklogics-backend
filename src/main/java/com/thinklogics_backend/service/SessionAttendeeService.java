package com.thinklogics_backend.service;

import org.springframework.stereotype.Service;



import com.thinklogics_backend.model.SessionAttendee;
import com.thinklogics_backend.repository.SessionAttendeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionAttendeeService {

    private final SessionAttendeeRepository attendeeRepository;

    @Autowired
    public SessionAttendeeService(SessionAttendeeRepository attendeeRepository) {
        this.attendeeRepository = attendeeRepository;
    }

    public List<SessionAttendee> getAllAttendees() {
        return attendeeRepository.findAll();
    }

    public Optional<SessionAttendee> getAttendeeById(Long id) {
        return attendeeRepository.findById(id);
    }

    public SessionAttendee createOrUpdateAttendee(SessionAttendee attendee) {
        return attendeeRepository.save(attendee);
    }

    public void deleteAttendeeById(Long id) {
        attendeeRepository.deleteById(id);
    }
    public boolean checkUserExistsByPhoneNumber(String phoneNumber) {
        return attendeeRepository.existsByPhoneNumber(phoneNumber);
    }
}

