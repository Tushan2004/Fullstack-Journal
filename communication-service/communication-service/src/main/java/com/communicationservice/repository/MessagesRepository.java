package com.communicationservice.repository;

import com.communicationservice.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagesRepository extends JpaRepository<Messages, Long> {

    // Use the real field names: senderId, receiverId
    List<Messages> findBySenderIdOrReceiverIdOrderByDateSentAsc(Long senderId, Long receiverId);

    // This one is fine (uses parent.id)
    List<Messages> findByParent_IdOrderByDateSentAsc(Long parentId);
}