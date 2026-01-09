package se.labb.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notation")
@Cacheable
public class Notation extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(columnDefinition = "TEXT")
    public String notation;

    public String diagnosis;

    public LocalDateTime date;

    @Column(name = "creator_user_id")
    public Long creatorUserId;

    @Column(name = "receiver_user_id")
    public Long receiverUserId;
}