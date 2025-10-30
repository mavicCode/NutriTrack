package com.software.nutritrack.controller;

import com.software.nutritrack.dto.request.ClienteRequestDTO;
import com.software.nutritrack.dto.response.ClienteResponseDTO;
import com.software.nutritrack.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    // ========================================
    // 🔹 Obtener todos los clientes
    // ========================================
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
        List<ClienteResponseDTO> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    // ========================================
    // 🔹 Obtener cliente por ID
    // ========================================
    @GetMapping("/{clienteId}")
    public ResponseEntity<ClienteResponseDTO> obtenerCliente(
            @PathVariable UUID clienteId
    ) {
        ClienteResponseDTO cliente = clienteService.obtenerCliente(clienteId);
        return ResponseEntity.ok(cliente);
    }

    // ========================================
    // 🔹 Actualizar cliente
    // ========================================
    @PutMapping("/{clienteId}")
    public ResponseEntity<ClienteResponseDTO> actualizarCliente(
            @PathVariable UUID clienteId,
            @Valid @RequestBody ClienteRequestDTO request
    ) {
        ClienteResponseDTO actualizado = clienteService.actualizarCliente(clienteId, request);
        return ResponseEntity.ok(actualizado);
    }

    // ========================================
    // 🔹 Eliminar cliente
    // ========================================
    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Map<String, String>> eliminarCliente(
            @PathVariable UUID clienteId
    ) {
        clienteService.eliminarCliente(clienteId);
        return ResponseEntity.ok(Map.of("mensaje", "Cliente eliminado correctamente"));
    }
}
