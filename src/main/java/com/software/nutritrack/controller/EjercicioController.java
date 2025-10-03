package com.software.nutritrack.controller;

import com.software.nutritrack.dto.request.EjercicioRequestDTO;
import com.software.nutritrack.dto.response.EjercicioResponseDTO;
import com.software.nutritrack.service.EjercicioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/excercises")
@RequiredArgsConstructor
public class EjercicioController {

    private final EjercicioService ejercicioService;

    @PostMapping("/nuevo")
    public ResponseEntity<EjercicioResponseDTO> nuevo(@Valid @RequestBody EjercicioRequestDTO dto) {
        return ResponseEntity.ok(ejercicioService.nuevo(dto));
    }

    @GetMapping("/{id_ejercicio}")
    public ResponseEntity<EjercicioResponseDTO> getEjercicio(@PathVariable Long id_ejercicio) {
        return ResponseEntity.ok(ejercicioService.getEjercicio(id_ejercicio));
    }


    @PutMapping("/{id_ejercicio}")
    public ResponseEntity<EjercicioResponseDTO> editarEjercicio(
            @PathVariable Long id_ejercicio,
            @RequestBody EjercicioRequestDTO request
    ) {
        EjercicioResponseDTO updated = ejercicioService.editarEjercicio(id_ejercicio, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id_ejercicio}")
    public ResponseEntity<Map<String, String>> eliminarEjercicio(
            @PathVariable Long id_ejercicio
    ) {

        ejercicioService.eliminarEjercicio(id_ejercicio);
        return ResponseEntity.ok(Map.of("mensaje", "Ejercicio eliminado correctamente"));
    }

}