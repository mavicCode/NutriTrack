package com.software.nutritrack.dto.response;

import lombok.Builder;

@Builder
public record LoginResponseDTO(
        Long userId,
        String email,
        String token
) {}