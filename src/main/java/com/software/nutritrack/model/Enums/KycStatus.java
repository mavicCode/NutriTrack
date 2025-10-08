package com.software.nutritrack.model.Enums;

public enum KycStatus {
    PENDING,        // KYC pendiente de verificación
    VERIFIED,       // KYC verificado exitosamente
    REJECTED,       // KYC rechazado
    REQUIRES_UPDATE // Requiere actualización de documentos
}
