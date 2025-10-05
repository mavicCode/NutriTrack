package com.software.nutritrack.controller;

import com.software.nutritrack.dto.response.MetaResponseDTO;
import com.software.nutritrack.dto.response.ProgresoResponseDTO;
import com.software.nutritrack.model.Meta;
import com.software.nutritrack.service.MetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/metas")
@RequiredArgsConstructor
public class MetaController {

    private final MetaService metaService;

    // US05 / US07 – Crear meta
    @PostMapping
    public ResponseEntity<MetaResponseDTO> crearMeta(@RequestBody Meta meta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(metaService.crearMeta(meta));
    }

    // US08 – Ajustar meta
    @PutMapping("/{id}")
    public ResponseEntity<MetaResponseDTO> ajustarMeta(
            @PathVariable Long id,
            @RequestParam String descripcion) {
        return ResponseEntity.ok(metaService.ajustarMeta(id, descripcion));
    }

    // US06 – Registrar avance
    @PostMapping("/{id}/avances")
    public ResponseEntity<ProgresoResponseDTO> registrarAvance(
            @PathVariable Long id,
            @RequestParam boolean cumplido) {
        return ResponseEntity.status(HttpStatus.CREATED).body(metaService.registrarAvance(id, cumplido));
    }
}

