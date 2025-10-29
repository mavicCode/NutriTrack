package com.software.nutritrack.dto.response;

import java.time.LocalDate;

public record InformacionResponseDTO(
        Long idInformacion,
        String idUsuario,
        LocalDate fechaGeneracion,
        String formato,
        String rutaArchivo
) {}