package com.software.nutritrack.service;

import com.software.nutritrack.dto.request.InformacionClienteRequestDTO;
import com.software.nutritrack.dto.response.InformacionClienteResponseDTO;
import com.software.nutritrack.exception.BusinessRuleException;
import com.software.nutritrack.exception.ResourceNotFoundException;
import com.software.nutritrack.model.Cliente;
import com.software.nutritrack.model.InformacionCliente;
import com.software.nutritrack.repository.ClienteRepository;
import com.software.nutritrack.repository.InformacionClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InformacionClienteService {

    private final InformacionClienteRepository informacionClienteRepository;
    private final ClienteRepository clienteRepository;

    // ===========================
    // 🔹 Crear nueva información
    // ===========================
    @Transactional
    public InformacionClienteResponseDTO registrarInformacion(UUID idCliente, InformacionClienteRequestDTO request) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        if (informacionClienteRepository.existsByClienteId(idCliente)) {
            throw new BusinessRuleException("El cliente ya tiene información registrada");
        }

        InformacionCliente info = new InformacionCliente();
        info.setCliente(cliente);
        info.setEdad(request.getEdad());
        info.setSexo(request.getSexo());
        info.setAltura(request.getAltura());
        info.setPeso(request.getPeso());
        info.setNivelActividad(request.getNivelActividad());
        info.setFechaRegistro(LocalDate.now());

        // Calcular IMC
        if (request.getAltura() != null && request.getPeso() != null) {
            double alturaMetros = request.getAltura().doubleValue() / 100.0;
            double imc = request.getPeso().doubleValue() / (alturaMetros * alturaMetros);
            info.setImc(BigDecimal.valueOf(imc).setScale(2, RoundingMode.HALF_UP));
        }

        informacionClienteRepository.save(info);

        return new InformacionClienteResponseDTO(
                info.getId(),
                cliente.getId(),
                info.getEdad(),
                info.getSexo(),
                info.getAltura(),
                info.getPeso(),
                info.getImc(),
                info.getNivelActividad(),
                info.getFechaRegistro()
        );
    }

    // ===========================
    // 🔹 Obtener última información
    // ===========================
    @Transactional(readOnly = true)
    public InformacionClienteResponseDTO obtenerUltimaInformacion(UUID idCliente) {
        InformacionCliente info = informacionClienteRepository
                .findTopByClienteIdOrderByFechaRegistroDesc(idCliente)
                .orElseThrow(() -> new ResourceNotFoundException("No hay información registrada para este cliente"));

        return new InformacionClienteResponseDTO(
                info.getId(),
                info.getCliente().getId(),
                info.getEdad(),
                info.getSexo(),
                info.getAltura(),
                info.getPeso(),
                info.getImc(),
                info.getNivelActividad(),
                info.getFechaRegistro()
        );
    }

    // ===========================
    // 🔹 Obtener historial completo
    // ===========================
    @Transactional(readOnly = true)
    public List<InformacionCliente> obtenerHistorial(UUID idCliente) {
        return informacionClienteRepository.findByClienteId(idCliente);
    }

    // ===========================
    // 🔹 Actualizar información
    // ===========================
    @Transactional
    public InformacionClienteResponseDTO actualizarInformacion(Long idInfo, InformacionClienteRequestDTO request) {
        InformacionCliente info = informacionClienteRepository.findById(idInfo)
                .orElseThrow(() -> new ResourceNotFoundException("Información no encontrada"));

        info.setEdad(request.getEdad());
        info.setSexo(request.getSexo());
        info.setAltura(request.getAltura());
        info.setPeso(request.getPeso());
        info.setNivelActividad(request.getNivelActividad());
        info.setFechaRegistro(LocalDate.now());

        // Recalcular IMC
        if (request.getAltura() != null && request.getPeso() != null) {
            double alturaMetros = request.getAltura().doubleValue() / 100.0;
            double imc = request.getPeso().doubleValue() / (alturaMetros * alturaMetros);
            info.setImc(BigDecimal.valueOf(imc).setScale(2, RoundingMode.HALF_UP));
        }

        informacionClienteRepository.save(info);

        return new InformacionClienteResponseDTO(
                info.getId(),
                info.getCliente().getId(),
                info.getEdad(),
                info.getSexo(),
                info.getAltura(),
                info.getPeso(),
                info.getImc(),
                info.getNivelActividad(),
                info.getFechaRegistro()
        );
    }

    // ===========================
    // 🔹 Eliminar registro
    // ===========================
    @Transactional
    public void eliminarInformacion(Long idInfo) {
        if (!informacionClienteRepository.existsById(idInfo)) {
            throw new ResourceNotFoundException("Registro no encontrado");
        }
        informacionClienteRepository.deleteById(idInfo);
    }
}
