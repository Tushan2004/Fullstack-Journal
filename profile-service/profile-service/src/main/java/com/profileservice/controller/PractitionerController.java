package com.profileservice.controller;

import com.profileservice.dto.PatientCreateRequest;
import com.profileservice.entity.Practitioner;
import com.profileservice.repository.PractitionerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3001"})
@RequestMapping("/practitioners")
public class PractitionerController {

    private final PractitionerRepository practitionerRepository;

    public PractitionerController(PractitionerRepository practitionerRepository) {
        this.practitionerRepository = practitionerRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<Practitioner> createPractitioner(@RequestBody PatientCreateRequest request) {
        Practitioner practitioner = new Practitioner();
        practitioner.setUserId(request.getUserId());
        practitioner.setFirstName(request.getFirstName());
        practitioner.setLastName(request.getLastName());
        Practitioner saved = practitionerRepository.save(practitioner);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Practitioner> getPractitionerById(@PathVariable Long id) {
        return practitionerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /*@GetMapping("/by-user/{userId}")
    public ResponseEntity<Practitioner> getPractitionerByUserId(@PathVariable Long userId) {
        return practitionerRepository.findByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }*/

    @GetMapping
    public List<Practitioner> getAllPractitioners() {
        return practitionerRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Practitioner> updatePractitioner(@PathVariable Long id,
                                                           @RequestBody Practitioner updated) {
        return practitionerRepository.findById(id)
                .map(existing -> {
                    existing.setFirstName(updated.getFirstName());
                    existing.setLastName(updated.getLastName());
                    existing.setUserId(updated.getUserId());
                    existing.setRole(updated.getRole());
                    Practitioner saved = practitionerRepository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePractitioner(@PathVariable Long id) {
        if (!practitionerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        practitionerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}