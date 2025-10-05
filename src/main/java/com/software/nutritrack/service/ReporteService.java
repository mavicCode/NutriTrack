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
        List<Plan> planes = planRepository.findByUsuarioIdAndFecha(userId, fecha);

        if (planes.isEmpty()) {
            throw new ResourceNotFoundException("No hay registros de comidas para esta fecha");
        }

        Map<String, Double> consumoPorCategoria = planes.stream()
                .collect(Collectors.groupingBy(
                        this::getCategoriaNombre,
                        Collectors.summingDouble(p -> 1.0)
                ));

        return new ConsumoReporteResponseDTO(fecha, consumoPorCategoria, 0.0);
    }

    public ComparacionReporteResponseDTO getComparison(Long userId, LocalDate fecha) {
        List<Plan> planes = planRepository.findByUsuarioIdAndFecha(userId, fecha);

        if (planes.isEmpty()) {
            throw new ResourceNotFoundException("No hay registros de comidas para esta fecha");
        }

        double consumido = planes.size() * 500.0;
        double metaCalorias = 2000.0;
        double cumplimiento = (consumido / metaCalorias) * 100;

        String mensaje = cumplimiento >= 90 && cumplimiento <= 110
                ? "Estás dentro del objetivo"
                : cumplimiento < 90
                ? "Estás por debajo del objetivo"
                : "Estás por encima del objetivo";

        return new ComparacionReporteResponseDTO(metaCalorias, consumido, Math.round(cumplimiento), mensaje);
    }

    public TendenciaReporteResponseDTO getTrends(Long userId, String rango) {
        LocalDate fechaFin = LocalDate.now();
        LocalDate fechaInicio = rango.equalsIgnoreCase("semanal")
                ? fechaFin.minusDays(7)
                : fechaFin.minusDays(30);

        List<Plan> planes = planRepository.findByUsuarioIdAndFechaBetween(userId, fechaInicio, fechaFin);

        if (planes.isEmpty()) {
            throw new ResourceNotFoundException("No hay datos suficientes para mostrar tendencias");
        }

        Map<LocalDate, Double> caloriasPorFecha = planes.stream()
                .collect(Collectors.groupingBy(
                        Plan::getFecha,
                        Collectors.summingDouble(p -> 500.0)
                ));

        List<LocalDate> fechas = new ArrayList<>(caloriasPorFecha.keySet());
        Collections.sort(fechas);

        List<Double> calorias = fechas.stream()
                .map(caloriasPorFecha::get)
                .collect(Collectors.toList());

        return new TendenciaReporteResponseDTO(fechas, calorias, rango);
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