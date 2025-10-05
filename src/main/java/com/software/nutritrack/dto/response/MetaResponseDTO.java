package com.software.nutritrack.dto.response;

import lombok.Builder;

@Builder
public record MetaResponseDTO(
        Long id,
        String tipo,
        String descripcion,
        String estado
) {}

