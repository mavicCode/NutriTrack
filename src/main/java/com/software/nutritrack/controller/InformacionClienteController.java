package com.software.nutritrack.controller;

import com.software.nutritrack.dto.request.InformacionClienteRequestDTO;
import com.software.nutritrack.dto.response.InformacionClienteResponseDTO;
import com.software.nutritrack.service.InformacionClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class InformacionClienteController {

    private final InformacionClienteService informacionClienteService;

    // ========================================
    // 🔹 Crear nueva información física
    // ========================================
    @PostMapping("/{clienteId}/informacion")
    public ResponseEntity<InformacionClienteResponseDTO> registrarInformacion(
            @PathVariable UUID clienteId,
            @RequestBody InformacionClienteRequestDTO request
    ) {
        var response = informacionClienteService.registrarInformacion(clienteId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ========================================
    // 🔹 Obtener el historial completo
    // ========================================
    @GetMapping("/{clienteId}/informacion")
    public ResponseEntity<List<InformacionClienteResponseDTO>> listarHistorial(
            @PathVariable UUID clienteId
    ) {
        var response = informacionClienteService.obtenerHistorial(clienteId)
                .stream()
                .map(info -> new InformacionClienteResponseDTO(
                        info.getId(),
                        info.getCliente().getId(),
                        info.getEdad(),
                        info.getSexo(),
                        info.getAltura(),
                        info.getPeso(),
                        info.getImc(),
                        info.getNivelActividad(),
                        info.getFechaRegistro()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }

    // ========================================
    // 🔹 Obtener la última información
    // ========================================
    @GetMapping("/{clienteId}/informacion/ultima")
    public ResponseEntity<InformacionClienteResponseDTO> obtenerUltima(
            @PathVariable UUID clienteId
    ) {
        var response = informacionClienteService.obtenerUltimaInformacion(clienteId);
        return ResponseEntity.ok(response);
    }

    // ========================================
    // 🔹 Actualizar información existente
    // ========================================
    @PutMapping("/informacion/{infoId}")
    public ResponseEntity<InformacionClienteResponseDTO> actualizarInformacion(
            @PathVariable Long infoId,
            @RequestBody InformacionClienteRequestDTO request
    ) {
        var response = informacionClienteService.actualizarInformacion(infoId, request);
        return ResponseEntity.ok(response);
    }

    // ========================================
    // 🔹 Eliminar un registro de información
    // ========================================
    @DeleteMapping("/informacion/{infoId}")
    public ResponseEntity<Map<String, String>> eliminarInformacion(
            @PathVariable Long infoId
    ) {
        informacionClienteService.eliminarInformacion(infoId);
        return ResponseEntity.ok(Map.of("mensaje", "Registro de información eliminado correctamente"));
    }
}
