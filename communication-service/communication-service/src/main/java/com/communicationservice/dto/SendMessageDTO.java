package com.communicationservice.dto;

public record SendMessageDTO(
        Long senderUserId,
        Long receiverUserId,
        String message,
        Long parentId // nullable
) {}
