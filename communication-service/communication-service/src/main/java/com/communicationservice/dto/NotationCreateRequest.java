package com.communicationservice.dto;

// com.communicationservice.dto.NotationCreateRequest
public record NotationCreateRequest(
        Long creatorUserId,
        Long receiverUserId,
        String notation,
        String diagnosis
) {}
