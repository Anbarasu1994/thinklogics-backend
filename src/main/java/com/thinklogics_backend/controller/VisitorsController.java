package com.thinklogics_backend.controller;

import com.thinklogics_backend.model.User;
import com.thinklogics_backend.model.Visitors;
import com.thinklogics_backend.repository.VisitorsRepository;
import com.thinklogics_backend.service.VisitorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/visitors")
public class VisitorsController {

    @Autowired
    private VisitorsRepository visitorsRepository;
    private final VisitorsService visitorsService;

    @Autowired
    public VisitorsController(VisitorsService visitorsService) {
        this.visitorsService = visitorsService;
    }



    @GetMapping("/checkEmail")
    public ResponseEntity<Map<String, Boolean>> checkUserExists(@RequestParam String email) {
        Map<String, Boolean> response = new HashMap<>();
        boolean exists = visitorsRepository.existsByEmail(email);
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<String> saveVisitor(@RequestBody Visitors newVisitor) {
        if (visitorsRepository.existsByEmail(newVisitor.getEmail())) {
            return ResponseEntity.badRequest().body("Visitor already exists");
        } else {
            visitorsRepository.save(newVisitor);
            return ResponseEntity.ok("Visitor saved successfully");
        }
    }


    @GetMapping
    public ResponseEntity<List<Visitors>> getAllVisitors() {
        List<Visitors> visitors = visitorsService.getAllVisitors();
        return ResponseEntity.ok(visitors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Visitors> getVisitorById(@PathVariable Long id) {
        return visitorsService.getVisitorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Visitors> updateVisitor(@PathVariable Long id, @RequestBody Visitors visitor) {
        if (!visitorsService.getVisitorById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        visitor.setId(id);
        Visitors updatedVisitor = visitorsService.saveVisitor(visitor);
        return ResponseEntity.ok(updatedVisitor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisitor(@PathVariable Long id) {
        if (!visitorsService.getVisitorById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        visitorsService.deleteVisitor(id);
        return ResponseEntity.noContent().build();
    }

}