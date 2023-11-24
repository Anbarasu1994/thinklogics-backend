package com.thinklogics_backend.service;

import com.thinklogics_backend.model.Visitors;
import com.thinklogics_backend.repository.VisitorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisitorsService {

    private final VisitorsRepository visitorsRepository;

    @Autowired
    public VisitorsService(VisitorsRepository visitorsRepository) {
        this.visitorsRepository = visitorsRepository;
    }

    public List<Visitors> getAllVisitors() {
        return visitorsRepository.findAll();
    }

    public Optional<Visitors> getVisitorById(Long id) {
        return visitorsRepository.findById(id);
    }

    public Visitors saveVisitor(Visitors visitor) {
        return visitorsRepository.save(visitor);
    }

    public void deleteVisitor(Long id) {
        visitorsRepository.deleteById(id);
    }
    public boolean isEmailRegistered(String email) {
        Optional<Visitors> existingVisitor = visitorsRepository.findByEmail(email);
        return existingVisitor.isPresent();
    }
}
