package com.software.nutritrack.service;

import com.software.nutritrack.dto.request.MetaRequestDTO;
import com.software.nutritrack.dto.response.MetaResponseDTO;
import com.software.nutritrack.dto.response.ProgresoResponseDTO;
import com.software.nutritrack.model.Meta;
import com.software.nutritrack.model.Progreso;
import com.software.nutritrack.model.Usuario;
import com.software.nutritrack.repository.MetaRepository;
import com.software.nutritrack.repository.ProgresoRepository;
import com.software.nutritrack.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MetaService {

    private final MetaRepository metaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProgresoRepository progresoRepository;

    @Transactional
    public MetaResponseDTO crearMeta(MetaRequestDTO metaRequest) {
        // Buscar el usuario
        Usuario usuario = usuarioRepository.findById(metaRequest.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + metaRequest.getIdUsuario()));

        // Crear la meta
        Meta meta = new Meta();
        meta.setTipo(metaRequest.getTipo());
        meta.setDescripcion(metaRequest.getDescripcion());
        meta.setEstado(metaRequest.getEstado());
        meta.setFecha(metaRequest.getFecha());
        meta.setUsuario(usuario);

        // Guardar
        Meta metaGuardada = metaRepository.save(meta);

        // Convertir a DTO de respuesta
        return convertirAMetaResponseDTO(metaGuardada);
    }

    @Transactional
    public MetaResponseDTO ajustarMeta(Long id, String descripcion) {
        // Buscar la meta
        Meta meta = metaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meta no encontrada con ID: " + id));

        // Actualizar descripción
        meta.setDescripcion(descripcion);

        // Guardar cambios
        Meta metaActualizada = metaRepository.save(meta);

        // Convertir a DTO de respuesta
        return convertirAMetaResponseDTO(metaActualizada);
    }

    @Transactional
    public ProgresoResponseDTO registrarAvance(Long id, boolean cumplido) {
        // Buscar la meta
        Meta meta = metaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meta no encontrada con ID: " + id));

        // Calcular porcentaje (puedes ajustar esta lógica según tus necesidades)
        double porcentaje = cumplido ? 100.0 : 50.0;

        // Generar recomendaciones (puedes mejorar esta lógica)
        String recomendaciones = cumplido
                ? "¡Excelente! Sigue así para alcanzar tus objetivos."
                : "No te desanimes, intenta nuevamente mañana.";

        // Crear el progreso
        Progreso progreso = new Progreso();
        progreso.setFecha(LocalDate.now());
        progreso.setCumplido(cumplido);
        progreso.setPorcentaje(porcentaje);
        progreso.setRecomendaciones(recomendaciones);
        progreso.setMeta(meta);

        // Guardar
        Progreso progresoGuardado = progresoRepository.save(progreso);

        // Convertir a DTO de respuesta
        return convertirAProgresoResponseDTO(progresoGuardado);
    }

    private MetaResponseDTO convertirAMetaResponseDTO(Meta meta) {
        MetaResponseDTO dto = new MetaResponseDTO();
        dto.setId(meta.getId());
        dto.setTipo(meta.getTipo());
        dto.setDescripcion(meta.getDescripcion());
        dto.setEstado(meta.getEstado());
        dto.setFecha(meta.getFecha());
        return dto;
    }

    private ProgresoResponseDTO convertirAProgresoResponseDTO(Progreso progreso) {
        return ProgresoResponseDTO.builder()
                .id(progreso.getId())
                .fecha(progreso.getFecha())
                .cumplido(progreso.isCumplido())
                .porcentaje(progreso.getPorcentaje())
                .recomendaciones(progreso.getRecomendaciones())
                .build();
    }
}