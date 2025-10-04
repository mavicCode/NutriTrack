package com.software.nutritrack.dto.response;

import lombok.Builder;
import java.util.Map;

@Builder
public record ConsumoReporteResponseDTO(
        Double totalCalorias,
        Map<String, Double> categorias
) {}