package com.software.nutritrack.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record RegistroComidaRequestDTO(
        @NotNull(message = "El ID del usuario es obligatorio")
        Long usuarioId,

        @NotNull(message = "El ID del alimento es obligatorio")
        Long alimentoId,

        @NotNull(message = "La fecha es obligatoria")
        LocalDate fecha,

        @NotNull(message = "El tipo de comida es obligatorio")
        @Positive(message = "El tipo de comida debe ser positivo")
        Integer tipoComida,

        @NotNull(message = "La cantidad es obligatoria")
        @Positive(message = "La cantidad debe ser positiva")
        Double cantidad
) {}