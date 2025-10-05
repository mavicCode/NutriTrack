package com.software.nutritrack.exception;

public class MetaNotActiveException extends RuntimeException {
    public MetaNotActiveException(Long id) {
        super("La meta con id " + id + " no está activa y no puede ajustarse.");
    }
}

