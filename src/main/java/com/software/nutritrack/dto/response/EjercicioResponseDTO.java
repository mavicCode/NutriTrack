package com.software.nutritrack.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EjercicioResponseDTO(
        Long id_ejercicio,
        String descripcion,
        String categoria,
        LocalDate fecha_actualizacion
) {}