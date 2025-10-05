package com.software.nutritrack.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import com.software.nutritrack.dto.response.*;
import com.software.nutritrack.exception.ResourceNotFoundException;
import com.software.nutritrack.model.Informacion;
import com.software.nutritrack.model.Plan;
import com.software.nutritrack.repository.InformacionRepository;
import com.software.nutritrack.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
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

    public ResponseEntity<byte[]> generatePdf(Long userId, String rango) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, baos);

            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Reporte Nutricional - NutriTrack", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            document.add(new Paragraph("Usuario ID: " + userId));
            document.add(new Paragraph("Rango: " + rango));
            document.add(new Paragraph("Fecha de generación: " + LocalDate.now()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Resumen de Alimentación"));
            document.add(new Paragraph("Este reporte contiene información sobre tu progreso nutricional."));

            document.close();

            Informacion info = Informacion.builder()
                    .idUsuario(userId)
                    .formato("PDF")
                    .rutaArchivo("/reports/user_" + userId + "_" + rango + "_" + System.currentTimeMillis() + ".pdf")
                    .build();
            informacionRepository.save(info);

            byte[] pdfBytes = baos.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment",
                    "reporte_nutritrack_" + userId + "_" + rango + ".pdf");
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF: " + e.getMessage());
        }
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