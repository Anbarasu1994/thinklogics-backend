package com.thinklogics_backend.controller;

import com.thinklogics_backend.model.Session;
import com.thinklogics_backend.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/Admin/api/sessions")
public class SessionController {
    @Autowired
    private SessionService sessionService;

    @PostMapping("/create")
    public ResponseEntity<?> createSession(@RequestBody Session session) {
        try {
            // Call a service to create the session
            Session createdSession = sessionService.createSession(session);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Session creation failed");
        }
    }
    // Endpoint to get a list of created sessions
    @GetMapping("/list")
    public ResponseEntity<List<Session>> getAllSessions() {
        List<Session> sessions = sessionService.getAllSessions();
        return ResponseEntity.ok(sessions);
    }
   @GetMapping("/{id}")
    public Optional<Session> getSessionById(@PathVariable Long id){
        return sessionService.getById(id);
   }

    // Update an existing session
    @PutMapping("/{id}")
    public ResponseEntity<Session> updateSession(@PathVariable Long id, @RequestBody Session updatedSession) {
        Session session = sessionService.updateSession(id, updatedSession);
        return ResponseEntity.ok(session);
    }

    // Delete a session by ID and return a success message
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSession(@PathVariable Long id) {
        String deletedSession = sessionService.deleteSession(id);
        if (deletedSession != null) {
            return ResponseEntity.ok("Session with ID " + id + " deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
