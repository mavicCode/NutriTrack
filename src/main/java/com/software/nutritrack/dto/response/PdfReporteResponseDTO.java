package com.software.nutritrack.dto.response;

import lombok.Builder;

@Builder
public record PdfReporteResponseDTO(
        String url,
        String mensaje
) {}