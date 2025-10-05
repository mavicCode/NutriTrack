package com.software.nutritrack.service;

import com.software.nutritrack.dto.response.MetaResponseDTO;
import com.software.nutritrack.dto.response.ProgresoResponseDTO;
import com.software.nutritrack.exception.MetaNotActiveException;
import com.software.nutritrack.exception.MetaNotFoundException;
import com.software.nutritrack.model.*;
import com.software.nutritrack.repository.MetaRepository;
import com.software.nutritrack.repository.ProgresoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MetaService {

    private final MetaRepository metaRepository;
    private final ProgresoRepository progresoRepository;

    // US05/US07: Crear meta
    public MetaResponseDTO crearMeta(Meta meta) {
        if (meta.getDescripcion() == null || meta.getDescripcion().isBlank()) {
            throw new IllegalArgumentException("La descripción es obligatoria");
        }
        meta.setEstado(EstadoMeta.ACTIVO);
        meta.setFecha(LocalDate.now());
        Meta guardada = metaRepository.save(meta);
        return mapToDTO(guardada);
    }

    // US08 + RN06: Ajustar metas solo si están activas
    public MetaResponseDTO ajustarMeta(Long metaId, String nuevaDescripcion) {
        Meta meta = metaRepository.findById(metaId)
                .orElseThrow(() -> new MetaNotFoundException(metaId));

        if (meta.getEstado() != EstadoMeta.ACTIVO) {
            throw new MetaNotActiveException(metaId);
        }

        meta.setDescripcion(nuevaDescripcion);
        Meta guardada = metaRepository.save(meta);
        return mapToDTO(guardada);
    }

    // US06 + RN05: Registrar avance y actualizar progreso automáticamente
    public ProgresoResponseDTO registrarAvance(Long metaId, boolean cumplido) {
        Meta meta = metaRepository.findById(metaId)
                .orElseThrow(() -> new MetaNotFoundException(metaId));

        if (meta.getEstado() != EstadoMeta.ACTIVO) {
            throw new MetaNotActiveException(metaId);
        }

        Progreso progreso = new Progreso();
        progreso.setFecha(LocalDate.now());
        progreso.setCumplido(cumplido);

        long total = meta.getProgresos().size() + 1;
        long cumplidos = meta.getProgresos().stream().filter(Progreso::isCumplido).count();
        if (cumplido) cumplidos++;
        progreso.setPorcentaje((cumplidos * 100.0) / total);

        progreso.setMeta(meta);
        Progreso saved = progresoRepository.save(progreso);

        return mapToDTO(saved);
    }

    // ----- Mapper helpers -----
    private MetaResponseDTO mapToDTO(Meta meta) {
        return MetaResponseDTO.builder()
                .id(meta.getId())
                .tipo(meta.getTipo().name())
                .descripcion(meta.getDescripcion())
                .estado(meta.getEstado().name())
                .build();
    }

    private ProgresoResponseDTO mapToDTO(Progreso progreso) {
        return ProgresoResponseDTO.builder()
                .id(progreso.getId())
                .fecha(progreso.getFecha())
                .cumplido(progreso.isCumplido())
                .porcentaje(progreso.getPorcentaje())
                .recomendaciones(progreso.getRecomendaciones())
                .build();
    }
}
