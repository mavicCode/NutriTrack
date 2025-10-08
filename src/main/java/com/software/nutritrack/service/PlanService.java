package com.software.nutritrack.service;

import com.software.nutritrack.dto.request.ComidaAlimentoDTO;
import com.software.nutritrack.dto.request.PlanRequestDTO;
import com.software.nutritrack.dto.response.ComidaAlimentoResponseDTO;
import com.software.nutritrack.dto.response.PlanResponseDTO;
import com.software.nutritrack.exception.BusinessRuleException;
import com.software.nutritrack.exception.ResourceNotFoundException;
import com.software.nutritrack.model.ComidaAlimento;
import com.software.nutritrack.model.Plan;
import com.software.nutritrack.repository.AlimentoRepository;
import com.software.nutritrack.repository.PlanRepository;
import com.software.nutritrack.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;
    private final UsuarioRepository usuarioRepository;
    private final AlimentoRepository alimentoRepository;

    @Transactional
    public PlanResponseDTO create(PlanRequestDTO dto) {
        // Validar que existe el usuario
        var usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Validar tipo de comida (1-4)
        if (dto.tipoComida() < 1 || dto.tipoComida() > 4) {
            throw new BusinessRuleException("Tipo de comida inválido. Debe ser entre 1 y 4");
        }

        // Crear el plan
        var plan = Plan.builder()
                .usuario(usuario)
                .tipoComida(dto.tipoComida())
                .descripcion(dto.descripcion())
                .clasificacion(dto.clasificacion())
                .fecha(dto.fecha())
                .comidas(new ArrayList<>())
                .build();

        // Guardar el plan primero
        plan = planRepository.save(plan);

        // Agregar alimentos al plan
        if (dto.alimentos() != null && !dto.alimentos().isEmpty()) {
            for (ComidaAlimentoDTO alimentoDTO : dto.alimentos()) {
                var alimento = alimentoRepository.findById(alimentoDTO.alimentoId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Alimento no encontrado con ID: " + alimentoDTO.alimentoId()));

                var comidaAlimento = ComidaAlimento.builder()
                        .plan(plan)
                        .alimento(alimento)
                        .cantidad(alimentoDTO.cantidad())
                        .build();

                plan.getComidas().add(comidaAlimento);
            }
        }

        // Guardar con los alimentos
        plan = planRepository.save(plan);

        return toDto(plan);
    }

    @Transactional(readOnly = true)
    public List<PlanResponseDTO> findAll() {
        return planRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PlanResponseDTO findById(Long id) {
        return planRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Plan no encontrado"));
    }

    @Transactional(readOnly = true)
    public List<PlanResponseDTO> findByUsuarioId(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        return planRepository.findByUsuarioIdWithComidas(usuarioId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PlanResponseDTO> findByUsuarioIdAndTipoComida(Long usuarioId, Integer tipoComida) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }

        if (tipoComida < 1 || tipoComida > 4) {
            throw new BusinessRuleException("Tipo de comida inválido. Debe ser entre 1 y 4");
        }

        return planRepository.findByUsuarioIdAndTipoComida(usuarioId, tipoComida).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PlanResponseDTO update(Long id, PlanRequestDTO dto) {
        var plan = planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan no encontrado"));

        // Validar usuario
        var usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Validar tipo de comida
        if (dto.tipoComida() < 1 || dto.tipoComida() > 4) {
            throw new BusinessRuleException("Tipo de comida inválido. Debe ser entre 1 y 4");
        }

        // Actualizar datos básicos
        plan.setUsuario(usuario);
        plan.setFecha(dto.fecha());
        plan.setTipoComida(dto.tipoComida());
        plan.setDescripcion(dto.descripcion());
        plan.setClasificacion(dto.clasificacion());

        // Limpiar comidas anteriores
        plan.getComidas().clear();

        // Agregar nuevas comidas
        if (dto.alimentos() != null && !dto.alimentos().isEmpty()) {
            for (ComidaAlimentoDTO alimentoDTO : dto.alimentos()) {
                var alimento = alimentoRepository.findById(alimentoDTO.alimentoId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Alimento no encontrado con ID: " + alimentoDTO.alimentoId()));

                var comidaAlimento = ComidaAlimento.builder()
                        .plan(plan)
                        .alimento(alimento)
                        .cantidad(alimentoDTO.cantidad())
                        .build();

                plan.getComidas().add(comidaAlimento);
            }
        }

        return toDto(planRepository.save(plan));
    }

    @Transactional
    public void delete(Long id) {
        if (!planRepository.existsById(id)) {
            throw new ResourceNotFoundException("Plan no encontrado");
        }
        planRepository.deleteById(id);
    }

    private PlanResponseDTO toDto(Plan plan) {
        List<ComidaAlimentoResponseDTO> alimentosDto = plan.getComidas().stream()
                .map(ca -> ComidaAlimentoResponseDTO.builder()
                        .id(ca.getId())
                        .alimentoId(ca.getAlimento().getId())
                        .alimentoNombre(ca.getAlimento().getName())
                        .categoria(ca.getAlimento().getCategoria())
                        .caloriasPorUnidad(ca.getAlimento().getCalorias())
                        .cantidad(ca.getCantidad())
                        .caloriasTotales(ca.getCaloriasTotales())
                        .build())
                .collect(Collectors.toList());

        int caloriasTotales = alimentosDto.stream()
                .mapToInt(ComidaAlimentoResponseDTO::caloriasTotales)
                .sum();

        return PlanResponseDTO.builder()
                .id(plan.getId())
                .usuarioId(plan.getUsuario().getIdUsuario())
                .nombreUsuario(plan.getUsuario().getNombre())
                .tipoComida(plan.getTipoComida())
                .tipoComidaNombre(getTipoComidaNombre(plan.getTipoComida()))
                .descripcion(plan.getDescripcion())
                .fecha(plan.getFecha())
                .clasificacion(plan.getClasificacion())
                .alimentos(alimentosDto)
                .caloriasTotales(caloriasTotales)
                .build();
    }

    private String getTipoComidaNombre(Integer tipoComida) {
        return switch (tipoComida) {
            case 1 -> "Desayuno";
            case 2 -> "Almuerzo";
            case 3 -> "Cena";
            case 4 -> "Snack";
            default -> "Desconocido";
        };
    }
}