package com.software.nutritrack.service;

import com.software.nutritrack.dto.response.*;
import com.software.nutritrack.exception.ResourceNotFoundException;
import com.software.nutritrack.model.Informacion;
import com.software.nutritrack.model.Plan;
import com.software.nutritrack.repository.InformacionRepository;
import com.software.nutritrack.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final PlanRepository planRepository;
    private final InformacionRepository informacionRepository;

    public ConsumoReporteResponseDTO getConsumption(Long userId, LocalDate fecha) {
        throw new UnsupportedOperationException(
                "Plan.java no tiene campo 'fecha'. Coordinar con Victor para agregarlo."
        );
    }

    public ComparacionReporteResponseDTO getComparison(Long userId, LocalDate fecha) {
        throw new UnsupportedOperationException(
                "Plan.java no tiene campos 'fecha' ni 'calorias'. Coordinar con Victor."
        );
    }

    public TendenciaReporteResponseDTO getTrends(Long userId, String rango) {
        throw new UnsupportedOperationException(
                "Plan.java no tiene campo 'fecha'. Coordinar con Victor para agregarlo."
        );
    }

    public PdfReporteResponseDTO generatePdf(Long userId, String rango) {
        Informacion info = Informacion.builder()
                .idUsuario(userId)
                .formato("PDF")
                .rutaArchivo("/reports/user_" + userId + "_" + rango + "_" + System.currentTimeMillis() + ".pdf")
                .build();

        informacionRepository.save(info);

        return PdfReporteResponseDTO.builder()
                .url("https://nutritrack.com" + info.getRutaArchivo())
                .mensaje("PDF generado correctamente")
                .build();
    }

    private String getCategoriaNombre(Plan plan) {
        return switch (plan.getClasificacion()) {
            case 1 -> "Proteínas";
            case 2 -> "Carbohidratos";
            case 3 -> "Grasas";
            default -> "Otros";
        };
    }
}