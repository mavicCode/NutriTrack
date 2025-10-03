package com.software.nutritrack.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record RegistroComidaResponseDTO(
        Long id,
        Long usuarioId,
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