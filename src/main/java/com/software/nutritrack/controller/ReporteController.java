package com.software.nutritrack.controller;

import com.software.nutritrack.dto.request.*;
import com.software.nutritrack.dto.response.*;
import com.software.nutritrack.service.ReporteService;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("reports")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/consumption")
    public ResponseEntity<ConsumoReporteResponseDTO> getConsumption(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        ConsumoReporteResponseDTO reporte = reporteService.getConsumption(userId, fecha);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/comparison")
    public ResponseEntity<ComparacionReporteResponseDTO> getComparison(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        ComparacionReporteResponseDTO reporte = reporteService.getComparison(userId, fecha);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/trends")
    public ResponseEntity<TendenciaReporteResponseDTO> getTrends(
            @RequestParam Long userId,
            @RequestParam String rango) {

        TendenciaReporteResponseDTO reporte = reporteService.getTrends(userId, rango);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> downloadPdf(
            @RequestParam Long userId,
            @RequestParam String rango) {

        return reporteService.generatePdf(userId, rango);
    }
}