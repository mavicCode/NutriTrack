package com.software.nutritrack.controller;

import com.software.nutritrack.dto.request.RegistroComidaRequestDTO;
import com.software.nutritrack.dto.response.RegistroComidaResponseDTO;
import com.software.nutritrack.dto.response.ResumenDiarioDTO;
import com.software.nutritrack.service.RegistroComidaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/record_food")
@RequiredArgsConstructor
public class RegistroComidaController {

    private final RegistroComidaService registroComidaService;

    // US 09: Registrar comida diaria
    @PostMapping
    public ResponseEntity<RegistroComidaResponseDTO> create(@Valid @RequestBody RegistroComidaRequestDTO dto) {
        RegistroComidaResponseDTO created = registroComidaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Obtener todos los registros
    @GetMapping
    public ResponseEntity<List<RegistroComidaResponseDTO>> findAll() {
        List<RegistroComidaResponseDTO> registros = registroComidaService.findAll();
        return ResponseEntity.ok(registros);
    }

    // Obtener un registro por ID
    @GetMapping("/{id}")
    public ResponseEntity<RegistroComidaResponseDTO> findById(@PathVariable Long id) {
        RegistroComidaResponseDTO registro = registroComidaService.findById(id);
        return ResponseEntity.ok(registro);
    }

    // US 09: Ver lista del día por fecha específica
    @GetMapping("/usuario/{usuarioId}/fecha/{fecha}")
    public ResponseEntity<List<RegistroComidaResponseDTO>> findByUsuarioIdAndFecha(
            @PathVariable Long usuarioId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<RegistroComidaResponseDTO> registros = registroComidaService.findByUsuarioIdAndFecha(usuarioId, fecha);
        return ResponseEntity.ok(registros);
    }

    // Ver lista del día actual
    @GetMapping("/usuario/{usuarioId}/hoy")
    public ResponseEntity<List<RegistroComidaResponseDTO>> findByUsuarioIdToday(@PathVariable Long usuarioId) {
        List<RegistroComidaResponseDTO> registros = registroComidaService.findByUsuarioIdAndFecha(usuarioId, LocalDate.now());
        return ResponseEntity.ok(registros);
    }

    // Ver registros por tipo de comida en una fecha
    @GetMapping("/usuario/{usuarioId}/fecha/{fecha}/tipo/{tipoComida}")
    public ResponseEntity<List<RegistroComidaResponseDTO>> findByUsuarioIdAndFechaAndTipoComida(
            @PathVariable Long usuarioId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @PathVariable Integer tipoComida) {
        List<RegistroComidaResponseDTO> registros = registroComidaService.findByUsuarioIdAndFechaAndTipoComida(usuarioId, fecha, tipoComida);
        return ResponseEntity.ok(registros);
    }

    // US 11: Editar comidas
    @PutMapping("/{id}")
    public ResponseEntity<RegistroComidaResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody RegistroComidaRequestDTO dto) {
        RegistroComidaResponseDTO updated = registroComidaService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Eliminar un registro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        registroComidaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // US 12: Ver resumen diario por fecha específica
    @GetMapping("/resumen/usuario/{usuarioId}/fecha/{fecha}")
    public ResponseEntity<ResumenDiarioDTO> getResumenDiario(
            @PathVariable Long usuarioId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        ResumenDiarioDTO resumen = registroComidaService.getResumenDiario(usuarioId, fecha);
        return ResponseEntity.ok(resumen);
    }

    // US 12: Ver resumen de hoy
    @GetMapping("/resumen/usuario/{usuarioId}/hoy")
    public ResponseEntity<ResumenDiarioDTO> getResumenHoy(@PathVariable Long usuarioId) {
        ResumenDiarioDTO resumen = registroComidaService.getResumenDiario(usuarioId, LocalDate.now());
        return ResponseEntity.ok(resumen);
    }
}