package com.software.nutritrack.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UsuarioPerfilResponseDTO(
        String id,
        String email,
        LocalDate fecha_registro,
        LocalDate fecha_actualizacion
) {}