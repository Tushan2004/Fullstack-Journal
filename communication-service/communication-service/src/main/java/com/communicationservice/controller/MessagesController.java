package com.communicationservice.controller;

import com.communicationservice.dto.SendMessageDTO;
import com.communicationservice.entity.Messages;
import com.communicationservice.repository.MessagesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3001", "http://localhost:5173"})
@RequestMapping("/messages")
public class MessagesController {

    private final MessagesRepository messagesRepository;

    public MessagesController(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    // Skicka nytt meddelande eller svar
    @PostMapping
    public ResponseEntity<Messages> send(@RequestBody SendMessageDTO dto) {
        Messages m = new Messages();
        m.setMessage(dto.message());
        m.setSenderId(dto.senderUserId());
        m.setReceiverId(dto.receiverUserId());
        m.setDateSent(LocalDateTime.now());

        if (dto.parentId() != null) {
            Messages parent = messagesRepository.findById(dto.parentId())
                    .orElseThrow(() -> new RuntimeException("Parent message not found"));
            m.setParent(parent);
        }

        Messages saved = messagesRepository.save(m);
        return ResponseEntity.ok(saved);
    }

    // Alla meddelanden där användaren är avsändare eller mottagare
    @GetMapping
    public List<Messages> getMessages(@RequestParam("userId") Long userId) {
        return messagesRepository
                .findBySenderIdOrReceiverIdOrderByDateSentAsc(userId, userId);
    }

    // Alla svar i en tråd
    @GetMapping("/replies/{messageId}")
    public List<Messages> getReplies(@PathVariable Long messageId) {
        return messagesRepository
                .findByParent_IdOrderByDateSentAsc(messageId);
    }
}