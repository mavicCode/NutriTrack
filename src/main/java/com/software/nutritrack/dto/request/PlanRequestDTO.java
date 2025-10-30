package com.software.nutritrack.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record PlanRequestDTO(
        @NotNull(message = "El ID del usuario es obligatorio")
        UUID usuarioId,

        @NotNull(message = "El tipo de comida es obligatorio")
        @Positive(message = "El tipo de comida debe ser positivo")
        Integer tipoComida,

        @Size(max = 400, message = "La descripción no puede exceder 400 caracteres")
        String descripcion,

        LocalDate fecha,

        Integer clasificacion,

        List<ComidaAlimentoDTO> alimentos
) {}