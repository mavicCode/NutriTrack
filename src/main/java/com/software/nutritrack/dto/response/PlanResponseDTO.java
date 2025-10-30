package com.software.nutritrack.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
public record PlanResponseDTO(
        Long id,
        UUID usuarioId,
        String nombreUsuario,
        Integer tipoComida,
        String tipoComidaNombre, // "Desayuno", "Almuerzo", etc.
        String descripcion,
        LocalDate fecha,
        Integer clasificacion,
        List<ComidaAlimentoResponseDTO> alimentos,
        Integer caloriasTotales
) {}