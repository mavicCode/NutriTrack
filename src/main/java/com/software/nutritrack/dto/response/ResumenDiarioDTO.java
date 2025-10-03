package com.software.nutritrack.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Builder
public record ResumenDiarioDTO(
        Long usuarioId,
        String nombreUsuario,
        LocalDate fecha,
        Integer caloriasTotales,
        Map<String, Integer> caloriasPorTipoComida,
        Map<String, Integer> caloriasPorCategoria,
        List<RegistroComidaResponseDTO> registros,
        boolean tieneRegistros,
        String mensaje
) {}