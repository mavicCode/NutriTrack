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

    //Listar todos los reportes de un usuario
    @GetMapping("/history")
    public ResponseEntity<List<InformacionResponseDTO>> getReportHistory(@RequestParam Long userId) {
        List<InformacionResponseDTO> historial = reporteService.getReportHistory(userId);
        return ResponseEntity.ok(historial);
    }

    //btener detalles de un reporte específico
    @GetMapping("/history/{reportId}")
    public ResponseEntity<InformacionResponseDTO> getReportDetails(@PathVariable Long reportId) {
        InformacionResponseDTO reporte = reporteService.getReportDetails(reportId);
        return ResponseEntity.ok(reporte);
    }

    //Registrar manualmente un reporte (opcional, para auditoría)
    @PostMapping("/history")
    public ResponseEntity<InformacionResponseDTO> createReportRecord(@RequestBody InformacionRequestDTO request) {
        InformacionResponseDTO reporte = reporteService.createReportRecord(request);
        return ResponseEntity.status(201).body(reporte);
    }

    //Actualizar información de un reporte
    @PutMapping("/history/{reportId}")
    public ResponseEntity<InformacionResponseDTO> updateReportRecord(
            @PathVariable Long reportId,
            @RequestBody InformacionRequestDTO request) {
        InformacionResponseDTO reporte = reporteService.updateReportRecord(reportId, request);
        return ResponseEntity.ok(reporte);
    }

    //Eliminar un reporte específico del historial
    @DeleteMapping("/history/{reportId}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long reportId) {
        reporteService.deleteReport(reportId);
        return ResponseEntity.noContent().build();
    }

    //Eliminar todos los reportes de un usuario
    @DeleteMapping("/history/user/{userId}")
    public ResponseEntity<Void> deleteAllUserReports(@PathVariable Long userId) {
        reporteService.deleteAllUserReports(userId);
        return ResponseEntity.noContent().build();
    }
}