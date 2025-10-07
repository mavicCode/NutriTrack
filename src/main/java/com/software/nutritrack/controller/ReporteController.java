package com.software.nutritrack.controller;

import com.software.nutritrack.dto.request.*;
import com.software.nutritrack.dto.response.*;
import com.software.nutritrack.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reports")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @PostMapping("/consumption")
    public ResponseEntity<ConsumoReporteResponseDTO> getConsumption(
            @RequestBody ConsumoReporteRequestDTO request) {

        ConsumoReporteResponseDTO reporte = reporteService.getConsumption(
                request.userId(),
                request.fecha()
        );
        return ResponseEntity.ok(reporte);
    }

    @PostMapping("/comparison")
    public ResponseEntity<ComparacionReporteResponseDTO> getComparison(
            @RequestBody ComparacionReporteRequestDTO request) {

        ComparacionReporteResponseDTO reporte = reporteService.getComparison(
                request.userId(),
                request.fecha()
        );
        return ResponseEntity.ok(reporte);
    }

    @PostMapping("/trends")
    public ResponseEntity<TendenciaReporteResponseDTO> getTrends(
            @RequestBody TendenciaReporteRequestDTO request) {

        TendenciaReporteResponseDTO reporte = reporteService.getTrends(
                request.userId(),
                request.rango()
        );
        return ResponseEntity.ok(reporte);
    }

    @PostMapping("/pdf")
    public ResponseEntity<byte[]> downloadPdf(
            @RequestBody PdfReporteRequestDTO request) {

        return reporteService.generatePdf(request.userId(), request.rango());
    }
}