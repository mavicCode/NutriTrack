package com.software.nutritrack.service;
import com.software.nutritrack.dto.request.EjercicioRequestDTO;
import com.software.nutritrack.dto.response.EjercicioResponseDTO;
import com.software.nutritrack.exception.BusinessRuleException;
import com.software.nutritrack.exception.ResourceNotFoundException;
import com.software.nutritrack.model.Ejercicio;
import com.software.nutritrack.repository.EjercicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EjercicioService {
    private final EjercicioRepository ejercicioRepository;

    @Transactional
    public EjercicioResponseDTO nuevo(EjercicioRequestDTO dto) {
        // Regla: no permitir descripcion duplicados
        if (ejercicioRepository.existsByDescripcion(dto.descripcion())) {
            throw new BusinessRuleException("El ejercicio ya está registrado");
        }

        // Crear ejercicio base
        var ejercicio = Ejercicio.builder()
                .descripcion(dto.descripcion())
                .categoria(dto.categoria())
                .build();

        var savedEjercicio = ejercicioRepository.save(ejercicio);

        // Respuesta
        return EjercicioResponseDTO.builder()
                .id_ejercicio(savedEjercicio.getIdEjercicio())
                .descripcion(savedEjercicio.getDescripcion())
                .categoria(savedEjercicio.getCategoria())
                .build();

    }

    @Transactional(readOnly = true)
    public EjercicioResponseDTO getEjercicio(Long id_ejercicio) {
        var ejercicio = ejercicioRepository.findById(id_ejercicio)
                .orElseThrow(() -> new ResourceNotFoundException("Ejercicio no encontrado"));

        return new EjercicioResponseDTO(
                ejercicio.getIdEjercicio(),
                ejercicio.getDescripcion(),
                ejercicio.getCategoria(),
                ejercicio.getFecha_actualizacion()
        );
    }

    @Transactional
    public EjercicioResponseDTO editarEjercicio(Long id_ejercicio, EjercicioRequestDTO request) {
        Ejercicio ejercicio = ejercicioRepository.findById(id_ejercicio)
                .orElseThrow(() -> new ResourceNotFoundException("Ejercicio no encontrado"));

        if (request.descripcion() != null && !request.descripcion().isEmpty()) {
            ejercicio.setDescripcion(request.descripcion());
        }
        if (request.categoria() != null && !request.categoria().isEmpty()) {
            ejercicio.setCategoria(request.categoria());
        }
        ejercicio.setFecha_actualizacion(LocalDate.now());

        ejercicioRepository.save(ejercicio);

        return new EjercicioResponseDTO(ejercicio.getIdEjercicio(), ejercicio.getDescripcion(),
                ejercicio.getCategoria(), ejercicio.getFecha_actualizacion());
    }

    public void eliminarEjercicio(Long id_ejercicio) {
        if (!ejercicioRepository.existsById(id_ejercicio)) {
            throw new ResourceNotFoundException("Ejercicio no encontrado");
        }
        ejercicioRepository.deleteById(id_ejercicio);
    }

}
