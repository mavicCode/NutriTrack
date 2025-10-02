package com.software.nutritrack.dto.request;

public record UsuarioUpdateRequestDTO(
        String nombre,
        Float peso,
        Float altura
) {}
