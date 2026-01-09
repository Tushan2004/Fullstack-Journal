package com.communicationservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notation")
public class Notation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String notation;

    @Column(nullable = false)
    private String diagnosis;

    @Column(nullable = false)
    private LocalDateTime date = LocalDateTime.now();

    // IDs of the users (or practitioners/patients) who created/received this
    @Column(name = "creator_user_id", nullable = false)
    private Long creatorUserId;

    @Column(name = "receiver_user_id", nullable = false)
    private Long receiverUserId;

    public Notation() {}

    public Notation(String notation,
                    String diagnosis,
                    Long creatorUserId,
                    Long receiverUserId) {
        this.notation = notation;
        this.diagnosis = diagnosis;
        this.date = LocalDateTime.now();
        this.creatorUserId = creatorUserId;
        this.receiverUserId = receiverUserId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNotation() { return notation; }
    public void setNotation(String notation) { this.notation = notation; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public Long getCreatorUserId() { return creatorUserId; }
    public void setCreatorUserId(Long creatorUserId) { this.creatorUserId = creatorUserId; }

    public Long getReceiverUserId() { return receiverUserId; }
    public void setReceiverUserId(Long receiverUserId) { this.receiverUserId = receiverUserId; }
}