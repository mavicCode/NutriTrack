package com.software.nutritrack.dto.response;

import lombok.Builder;

@Builder
public record UsuarioPerfilResponseDTO(
        Long id,
        String email,
        Float peso,
        Float altura
) {}