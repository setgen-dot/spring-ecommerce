package com.example.dto.response;

import java.time.Instant;

public record UserProfileResponse(
        Long id,
        String firstname,
        String lastname,
        String email,
        Instant createdAt
) {
}
