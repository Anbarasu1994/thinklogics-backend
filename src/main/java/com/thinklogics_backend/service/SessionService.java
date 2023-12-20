package com.thinklogics_backend.service;

import com.thinklogics_backend.model.Session;
import com.thinklogics_backend.model.User;
import com.thinklogics_backend.model.Visitors;
import com.thinklogics_backend.repository.SessionRepository;
import com.thinklogics_backend.repository.UserRepository;
import com.thinklogics_backend.repository.VisitorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SessionService {
    @Autowired
    private SessionRepository sessionRepository;
@Autowired
private VisitorsRepository visitorsRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository; // Assuming you have a UserRepository for user data

    @Transactional
    public Session createSession(Session session) {
        // Add any business logic here, if needed
        Session savedSession = sessionRepository.save(session);

        // After saving the session, email registered participants
        String sessionTitle = savedSession.getSessionTitle();
        String date = savedSession.getDate().toString();
        String time = savedSession.getTime();
        String location = savedSession.getPlatform(); // Change to 'getLocation()' if location is used

        // Retrieve email addresses of registered participants from the database
        List<User> registeredUsers = userRepository.findAll(); // Assuming User entity has email property
        // Include the meeting link in the email
        String meetLink = savedSession.getMeetLink();
       // Retrieve email addresses from the Visitor model
        List<Visitors> visitors = visitorsRepository.findAll(); // Assuming Visitor entity has email property

        // Create a set to hold unique email addresses
        Set<String> uniqueEmails = new HashSet<>();

        // Add User emails to the set
        for (User user : registeredUsers) {
            uniqueEmails.add(user.getEmail());
        }

        // Add Visitor emails to the set, but only if they don't exist in the User table
        for (Visitors visitor : visitors) {
            if (!uniqueEmails.contains(visitor.getEmail())) {
                uniqueEmails.add(visitor.getEmail());
            }
        }

        // Email each unique participant
        for (String email : uniqueEmails) {
            emailService.sendSessionCreatedEmail(sessionTitle, date, time, location, meetLink, email);
        }

        return savedSession;
    }
    // Method to retrieve a list of created sessions
    public List<Session> getAllSessions() {
        // You can customize this method further, for example, to filter or sort the sessions
        return sessionRepository.findAll();
    }
    public Optional<Session> getById(String id){
    return sessionRepository.findById(id);
    }

    public Session updateSession(String id, Session updatedSession) {
        Optional<Session> existingSession = sessionRepository.findById(id);

        if (existingSession.isPresent()) {
            // You can add validation or business logic here
            Session sessionToUpdate = existingSession.get();
            sessionToUpdate.setSessionTitle(updatedSession.getSessionTitle());
            sessionToUpdate.setSessionDescription(updatedSession.getSessionDescription());
            sessionToUpdate.setDate(updatedSession.getDate());
            sessionToUpdate.setTime(updatedSession.getTime());
            sessionToUpdate.setDuration(updatedSession.getDuration());
            sessionToUpdate.setHost(updatedSession.getHost());
            sessionToUpdate.setPlatform(updatedSession.getPlatform());
            sessionToUpdate.setMeetLink(updatedSession.getMeetLink());

            return sessionRepository.save(sessionToUpdate);
        } else {
            // Handle the case where the session with the given id is not found
            throw new RuntimeException("Session not found with id: " + id);
        }
    }


    public String deleteSession(String id) {
        Optional<Session> sessionOptional = sessionRepository.findById(id);

        if (sessionOptional.isPresent()) {
            sessionRepository.deleteById(id);
            return "Session with ID " + id + " deleted successfully.";
        } else {
            return null; // Or throw an exception if you prefer
        }
    }

}
