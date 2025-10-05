package com.software.nutritrack.dto.response;

import java.time.LocalDate;
import java.util.List;

public record TendenciaReporteResponseDTO(
        List<LocalDate> fechas,
        List<Double> calorias,
        String rango
) {}