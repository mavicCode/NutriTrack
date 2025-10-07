package com.software.nutritrack.service;

import com.software.nutritrack.dto.request.AlimentoRequestDTO;
import com.software.nutritrack.dto.response.AlimentoResponseDTO;
import com.software.nutritrack.exception.BusinessRuleException;
import com.software.nutritrack.exception.ResourceNotFoundException;
import com.software.nutritrack.model.Alimento;
import com.software.nutritrack.repository.AlimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlimentoService {

    private final AlimentoRepository alimentoRepository;

    @Transactional
    public AlimentoResponseDTO create(AlimentoRequestDTO dto) {
        // Regla: no permitir alimentos duplicados (mismo nombre)
        if (alimentoRepository.existsByName(dto.name())) {
            throw new BusinessRuleException("Ya existe un alimento con ese nombre");
        }

        // Validar que las calorías sean positivas
        if (dto.calorias() < 0) {
            throw new BusinessRuleException("Las calorías no pueden ser negativas");
        }

        //  Validar que el peso sea positivo
        if (dto.peso() <= 0) {
            throw new BusinessRuleException("El peso debe ser mayor a cero");
        }

        var alimento = Alimento.builder()
                .name(dto.name())
                .calorias(dto.calorias())
                .peso(dto.peso())
                .categoria(dto.categoria())
                .build();

        return toDto(alimentoRepository.save(alimento));
    }

    @Transactional(readOnly = true)
    public List<AlimentoResponseDTO> findAll() {
        return alimentoRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AlimentoResponseDTO findById(Long id) {
        return alimentoRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Alimento no encontrado"));
    }

    @Transactional(readOnly = true)
    public List<AlimentoResponseDTO> searchByName(String name) {
        return alimentoRepository.findByNameContaining(name).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AlimentoResponseDTO> findByCategoria(String categoria) {
        return alimentoRepository.findByCategoria(categoria).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<String> findAllCategorias() {
        return alimentoRepository.findDistinctCategorias();
    }

    @Transactional
    public AlimentoResponseDTO update(Long id, AlimentoRequestDTO dto) {
        var alimento = alimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alimento no encontrado"));

        // Validar nombre (solo si se envía)
        if (dto.name() != null && !dto.name().isBlank()) {
            alimentoRepository.findByName(dto.name())
                    .ifPresent(existing -> {
                        if (!existing.getId().equals(id)) {
                            throw new BusinessRuleException("Ya existe otro alimento con ese nombre");
                        }
                    });
            alimento.setName(dto.name());
        }

        // Validar calorías (solo si se envían)
        if (dto.calorias() != null) {
            if (dto.calorias() < 0) {
                throw new BusinessRuleException("Las calorías no pueden ser negativas");
            }
            alimento.setCalorias(dto.calorias());
        }

        // Validar peso (solo si se envía)
        if (dto.peso() != null) {
            if (dto.peso() <= 0) {
                throw new BusinessRuleException("El peso debe ser mayor a cero");
            }
            alimento.setPeso(dto.peso());
        }

        // Validar categoría (solo si se envía)
        if (dto.categoria() != null && !dto.categoria().isBlank()) {
            alimento.setCategoria(dto.categoria());
        }

        return toDto(alimentoRepository.save(alimento));
    }

    @Transactional
    public void delete(Long id) {
        if (!alimentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Alimento no encontrado");
        }
        alimentoRepository.deleteById(id);
    }

    private AlimentoResponseDTO toDto(Alimento alimento) {
        return AlimentoResponseDTO.builder()
                .id(alimento.getId())
                .name(alimento.getName())
                .calorias(alimento.getCalorias())
                .peso(alimento.getPeso())
                .categoria(alimento.getCategoria())
                .build();
    }
}