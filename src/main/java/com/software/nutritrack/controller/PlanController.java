package com.software.nutritrack.controller;

import com.software.nutritrack.dto.request.PlanRequestDTO;
import com.software.nutritrack.dto.response.PlanResponseDTO;
import com.software.nutritrack.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    // Crear un nuevo plan
    @PostMapping
    public ResponseEntity<PlanResponseDTO> create(@Valid @RequestBody PlanRequestDTO dto) {
        PlanResponseDTO created = planService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Obtener todos los planes
    @GetMapping
    public ResponseEntity<List<PlanResponseDTO>> findAll() {
        List<PlanResponseDTO> planes = planService.findAll();
        return ResponseEntity.ok(planes);
    }

    // Obtener un plan por ID
    @GetMapping("/{id}")
    public ResponseEntity<PlanResponseDTO> findById(@PathVariable Long id) {
        PlanResponseDTO plan = planService.findById(id);
        return ResponseEntity.ok(plan);
    }

    // Obtener planes de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PlanResponseDTO>> findByUsuarioId(@PathVariable Long usuarioId) {
        List<PlanResponseDTO> planes = planService.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(planes);
    }

    // Obtener planes de un usuario por tipo de comida
    @GetMapping("/usuario/{usuarioId}/tipo/{tipoComida}")
    public ResponseEntity<List<PlanResponseDTO>> findByUsuarioIdAndTipoComida(
            @PathVariable Long usuarioId,
            @PathVariable Integer tipoComida) {
        List<PlanResponseDTO> planes = planService.findByUsuarioIdAndTipoComida(usuarioId, tipoComida);
        return ResponseEntity.ok(planes);
    }

    // Actualizar un plan
    @PutMapping("/{id}")
    public ResponseEntity<PlanResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody PlanRequestDTO dto) {
        PlanResponseDTO updated = planService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Eliminar un plan
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        planService.delete(id);
        return ResponseEntity.noContent().build();
    }
}