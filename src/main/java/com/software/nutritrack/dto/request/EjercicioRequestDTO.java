package com.software.nutritrack.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record EjercicioRequestDTO(

        @NotBlank(message = "La descripcion es obligatoria")
        String descripcion,

        @NotBlank(message = "La categoria es obligatoria")
        String categoria
) {}