package com.software.nutritrack.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UsuarioPerfilResponseDTO(
        Long id,
        String email,
        String nombre,
        Float peso,
        Float altura,
        LocalDate fecha_registro
) {}