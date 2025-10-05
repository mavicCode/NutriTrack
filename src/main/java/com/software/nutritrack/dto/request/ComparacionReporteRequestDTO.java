package com.software.nutritrack.dto.request;

import java.time.LocalDate;

public record ComparacionReporteRequestDTO(
        Long userId,
        LocalDate fecha
) {}