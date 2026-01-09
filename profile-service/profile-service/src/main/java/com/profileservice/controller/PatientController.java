package com.profileservice.controller;

import com.profileservice.dto.PatientCreateRequest;
import com.profileservice.entity.Patient;
import com.profileservice.repository.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3001"})
@RequestMapping("/patients")
public class PatientController {

    private final PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // CREATE
    @PostMapping("/register")
    public ResponseEntity<Patient> createPatient(@RequestBody PatientCreateRequest request) {
        Patient patient = new Patient();
        patient.setUserId(request.getUserId());
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());

        Patient saved = patientRepository.save(patient);
        return ResponseEntity.ok(saved);
    }

    // READ by id
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        return patientRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // READ by userId (handy when frontend only knows userId)
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<Patient> getPatientByUserId(@PathVariable Long userId) {
        return patientRepository.findByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // READ all
    @GetMapping
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // UPDATE (simple version: overwrite fields)
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id,
                                                 @RequestBody Patient updated) {
        return patientRepository.findById(id)
                .map(existing -> {
                    existing.setFirstName(updated.getFirstName());
                    existing.setLastName(updated.getLastName());
                    existing.setUserId(updated.getUserId());
                    Patient saved = patientRepository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        if (!patientRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        patientRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public Patient getMyInfo(@RequestParam Long userId) {

        var patient = patientRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Patient profile not found"));

        return patient;
    }
}

