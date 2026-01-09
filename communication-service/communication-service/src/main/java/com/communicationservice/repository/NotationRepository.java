package com.communicationservice.repository;

import com.communicationservice.entity.Notation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotationRepository extends JpaRepository<Notation, Long> {
    List<Notation> findByCreatorUserIdOrReceiverUserId(Long creatorUserId, Long receiverUserId);
}
