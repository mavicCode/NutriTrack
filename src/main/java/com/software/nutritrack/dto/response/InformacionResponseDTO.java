package com.software.nutritrack.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record InformacionResponseDTO(
        Long idInformacion,
        UUID idUsuario,
        LocalDate fechaGeneracion,
        String formato,
        String rutaArchivo
) {}