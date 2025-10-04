package com.software.nutritrack.dto.response;

import lombok.Builder;
import java.time.LocalDate;
import java.util.List;

@Builder
public record TendenciaReporteResponseDTO(
        List<LocalDate> fechas,
        List<Double> calorias
) {}