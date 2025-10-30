package com.software.nutritrack.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record UsuarioPerfilResponseDTO(
        UUID id,
        String email,
        LocalDate fecha_registro,
        LocalDate fecha_actualizacion
) {}