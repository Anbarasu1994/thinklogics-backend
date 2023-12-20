package com.thinklogics_backend.controller;



import com.thinklogics_backend.model.SessionAttendee;
import com.thinklogics_backend.service.SessionAttendeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/attendees")
public class SessionAttendeeController {

    private final SessionAttendeeService attendeeService;

    @Autowired
    public SessionAttendeeController(SessionAttendeeService attendeeService) {
        this.attendeeService = attendeeService;
    }

    @GetMapping
    public ResponseEntity<List<SessionAttendee>> getAllAttendees() {
        List<SessionAttendee> attendees = attendeeService.getAllAttendees();
        return new ResponseEntity<>(attendees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionAttendee> getAttendeeById(@PathVariable Long id) {
        Optional<SessionAttendee> attendee = attendeeService.getAttendeeById(id);
        return attendee.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<SessionAttendee> createAttendee(@RequestBody SessionAttendee attendee) {
        SessionAttendee createdAttendee = attendeeService.createOrUpdateAttendee(attendee);
        return new ResponseEntity<>(createdAttendee, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessionAttendee> updateAttendee(@PathVariable Long id, @RequestBody SessionAttendee attendee) {
//        attendee.setId(id); // Assuming there's an ID field in the SessionAttendee class
        SessionAttendee updatedAttendee = attendeeService.createOrUpdateAttendee(attendee);
        return new ResponseEntity<>(updatedAttendee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendee(@PathVariable Long id) {
        attendeeService.deleteAttendeeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/exists/{phoneNumber}")
    public ResponseEntity<Boolean> checkUserExistsByPhoneNumber(@PathVariable String phoneNumber) {
        boolean userExists = attendeeService.checkUserExistsByPhoneNumber(phoneNumber);
        return new ResponseEntity<>(userExists, HttpStatus.OK);
    }
}