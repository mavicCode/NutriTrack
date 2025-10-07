package com.software.nutritrack.dto.request;

import java.time.LocalDate;

public record ConsumoReporteRequestDTO(
        Long userId,
        LocalDate fecha
) {}