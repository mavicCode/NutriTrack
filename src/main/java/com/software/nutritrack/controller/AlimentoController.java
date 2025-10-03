package com.software.nutritrack.controller;

import com.software.nutritrack.dto.request.AlimentoRequestDTO;
import com.software.nutritrack.dto.response.AlimentoResponseDTO;
import com.software.nutritrack.service.AlimentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foods")
@RequiredArgsConstructor
public class AlimentoController {

    private final AlimentoService alimentoService;

    @PostMapping
    public ResponseEntity<AlimentoResponseDTO> create(@Valid @RequestBody AlimentoRequestDTO dto) {
        AlimentoResponseDTO created = alimentoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<AlimentoResponseDTO>> findAll() {
        List<AlimentoResponseDTO> alimentos = alimentoService.findAll();
        return ResponseEntity.ok(alimentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlimentoResponseDTO> findById(@PathVariable Long id) {
        AlimentoResponseDTO alimento = alimentoService.findById(id);
        return ResponseEntity.ok(alimento);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AlimentoResponseDTO>> searchByName(@RequestParam String name) {
        List<AlimentoResponseDTO> alimentos = alimentoService.searchByName(name);
        return ResponseEntity.ok(alimentos);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<AlimentoResponseDTO>> findByCategoria(@PathVariable String categoria) {
        List<AlimentoResponseDTO> alimentos = alimentoService.findByCategoria(categoria);
        return ResponseEntity.ok(alimentos);
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<String>> findAllCategorias() {
        List<String> categorias = alimentoService.findAllCategorias();
        return ResponseEntity.ok(categorias);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlimentoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AlimentoRequestDTO dto) {
        AlimentoResponseDTO updated = alimentoService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alimentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}