package com.communicationservice.controller;

import com.communicationservice.dto.NotationCreateRequest;
import com.communicationservice.entity.Notation;
import com.communicationservice.repository.NotationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3001", "http://localhost:5173"})
@RequestMapping("/notations")
public class NotationController {

    private final NotationRepository notationRepository;

    public NotationController(NotationRepository notationRepository) {
        this.notationRepository = notationRepository;
    }

    @PostMapping
    public ResponseEntity<Notation> create(@RequestBody NotationCreateRequest dto) {
        Notation notation = new Notation(
                dto.notation(),
                dto.diagnosis(),
                dto.creatorUserId(),
                dto.receiverUserId()
        );

        Notation saved = notationRepository.save(notation);

        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<Notation> getNotations(@RequestParam("userId") Long userId) {
        var notations = notationRepository
                .findByCreatorUserIdOrReceiverUserId(userId, userId);
        return notations;
    }
}