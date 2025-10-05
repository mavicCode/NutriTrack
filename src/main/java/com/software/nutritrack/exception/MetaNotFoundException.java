package com.software.nutritrack.exception;

public class MetaNotFoundException extends RuntimeException {
    public MetaNotFoundException(Long id) {
        super("Meta no encontrada con id: " + id);
    }
}

