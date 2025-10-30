package com.software.nutritrack.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record InformacionRequestDTO(
        @NotNull(message = "El ID de usuario es obligatorio")
        UUID idUsuario,

        @NotBlank(message = "El formato es obligatorio")
        String formato,

        @NotBlank(message = "La ruta del archivo es obligatoria")
        String rutaArchivo
) {}