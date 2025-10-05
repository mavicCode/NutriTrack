package com.software.nutritrack.dto.response;

import java.time.LocalDate;
import java.util.Map;

public record ConsumoReporteResponseDTO(
        LocalDate fecha,
        Map<String, Double> consumoPorCategoria,
        Double totalCalorias
) {}