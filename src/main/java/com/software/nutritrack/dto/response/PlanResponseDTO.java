package com.software.nutritrack.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PlanResponseDTO(
        Long id,
        Long usuarioId,
        String nombreUsuario,
        Integer tipoComida,
        String tipoComidaNombre, // "Desayuno", "Almuerzo", etc.
        String descripcion,
        Integer clasificacion,
        List<ComidaAlimentoResponseDTO> alimentos,
        Integer caloriasTotales
) {}