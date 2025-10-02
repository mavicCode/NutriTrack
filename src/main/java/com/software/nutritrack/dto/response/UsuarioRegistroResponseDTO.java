package com.software.nutritrack.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UsuarioRegistroResponseDTO(

        // Datos del usuario creado
        Long userId,
        String email,

        // Datos del perfil creado
        Long profileId,
        String nombre,
        Float peso,
        Float altura,
        LocalDate fecha_registro
) {}