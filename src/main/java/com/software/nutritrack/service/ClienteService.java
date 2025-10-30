package com.software.nutritrack.service;

import com.software.nutritrack.dto.request.ClienteRequestDTO;
import com.software.nutritrack.dto.response.ClienteResponseDTO;
import com.software.nutritrack.exception.ResourceNotFoundException;
import com.software.nutritrack.model.Cliente;
import com.software.nutritrack.repository.ClienteRepository;
import com.software.nutritrack.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;


    // ===============================
    // 🔹 Obtener todos los clientes
    // ===============================
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(c -> new ClienteResponseDTO(
                        c.getId(),
                        c.getName(),
                        c.getFecha_inicio(),
                        c.getObjetivo_general(),
                        c.getUsuario().getId()
                ))
                .toList();
    }

    // ===============================
    // 🔹 Obtener cliente por ID
    // ===============================
    @Transactional(readOnly = true)
    public ClienteResponseDTO obtenerCliente(UUID idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getName(),
                cliente.getFecha_inicio(),
                cliente.getObjetivo_general(),
                cliente.getUsuario().getId()
        );
    }

    // ===============================
    // 🔹 Actualizar cliente
    // ===============================
    @Transactional
    public ClienteResponseDTO actualizarCliente(UUID idCliente, ClienteRequestDTO request) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        cliente.setName(request.getNombre());
        cliente.setObjetivo_general(request.getObjetivoGeneral());
        clienteRepository.save(cliente);

        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getName(),
                cliente.getFecha_inicio(),
                cliente.getObjetivo_general(),
                cliente.getUsuario().getId()
        );
    }

    // ===============================
    // 🔹 Eliminar cliente
    // ===============================
    @Transactional
    public void eliminarCliente(UUID idCliente) {
        if (!clienteRepository.existsById(idCliente)) {
            throw new ResourceNotFoundException("Cliente no encontrado");
        }
        clienteRepository.deleteById(idCliente);
    }
}
