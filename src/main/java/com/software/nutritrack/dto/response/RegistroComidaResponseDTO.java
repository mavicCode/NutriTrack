package com.software.nutritrack.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record RegistroComidaResponseDTO(
        Long id,
        UUID usuarioId,
        String nombreUsuario,
        Long alimentoId,
        String alimentoNombre,
        String categoria,
        LocalDate fecha,
        Integer tipoComida,
        String tipoComidaNombre,
        Double cantidad,
        Integer caloriasConsumidas,
        LocalDateTime fechaRegistro
) {}