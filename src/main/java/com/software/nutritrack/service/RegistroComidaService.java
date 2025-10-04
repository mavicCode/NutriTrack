package com.software.nutritrack.service;

import com.software.nutritrack.dto.request.RegistroComidaRequestDTO;
import com.software.nutritrack.dto.response.RegistroComidaResponseDTO;
import com.software.nutritrack.dto.response.ResumenDiarioDTO;
import com.software.nutritrack.exception.BusinessRuleException;
import com.software.nutritrack.exception.ResourceNotFoundException;
import com.software.nutritrack.model.RegistroComida;
import com.software.nutritrack.repository.AlimentoRepository;
import com.software.nutritrack.repository.RegistroComidaRepository;
import com.software.nutritrack.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistroComidaService {

    private final RegistroComidaRepository registroComidaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AlimentoRepository alimentoRepository;

    // US 09: Registrar comidas diarias
    @Transactional
    public RegistroComidaResponseDTO create(RegistroComidaRequestDTO dto) {
        // Validar usuario
        var usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Validar alimento
        var alimento = alimentoRepository.findById(dto.alimentoId())
                .orElseThrow(() -> new ResourceNotFoundException("Alimento no encontrado"));

        // Validar tipo de comida (1-4)
        if (dto.tipoComida() < 1 || dto.tipoComida() > 4) {
            throw new BusinessRuleException("Tipo de comida inválido. Debe ser entre 1 y 4");
        }

        // Validar cantidad
        if (dto.cantidad() <= 0) {
            throw new BusinessRuleException("La cantidad debe ser mayor a cero");
        }

        // Crear registro
        var registro = RegistroComida.builder()
                .usuario(usuario)
                .alimento(alimento)
                .fecha(dto.fecha())
                .tipoComida(dto.tipoComida())
                .cantidad(dto.cantidad())
                .build();

        return toDto(registroComidaRepository.save(registro));
    }

    // US 09: Obtener registros del día
    @Transactional(readOnly = true)
    public List<RegistroComidaResponseDTO> findByUsuarioIdAndFecha(Long usuarioId, LocalDate fecha) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }

        return registroComidaRepository.findByUsuarioIdAndFecha(usuarioId, fecha).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RegistroComidaResponseDTO> findAll() {
        return registroComidaRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RegistroComidaResponseDTO findById(Long id) {
        return registroComidaRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Registro no encontrado"));
    }

    @Transactional(readOnly = true)
    public List<RegistroComidaResponseDTO> findByUsuarioIdAndFechaAndTipoComida(
            Long usuarioId, LocalDate fecha, Integer tipoComida) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }

        if (tipoComida < 1 || tipoComida > 4) {
            throw new BusinessRuleException("Tipo de comida inválido. Debe ser entre 1 y 4");
        }

        return registroComidaRepository.findByUsuarioIdAndFechaAndTipoComida(usuarioId, fecha, tipoComida).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // US 11: Editar comidas
    @Transactional
    public RegistroComidaResponseDTO update(Long id, RegistroComidaRequestDTO dto) {
        var registro = registroComidaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro no encontrado"));

        // Validar alimento
        var alimento = alimentoRepository.findById(dto.alimentoId())
                .orElseThrow(() -> new ResourceNotFoundException("Alimento no encontrado"));

        // Validar tipo de comida
        if (dto.tipoComida() < 1 || dto.tipoComida() > 4) {
            throw new BusinessRuleException("Tipo de comida inválido. Debe ser entre 1 y 4");
        }

        // Validar cantidad
        if (dto.cantidad() <= 0) {
            throw new BusinessRuleException("La cantidad debe ser mayor a cero");
        }

        // Actualizar registro
        registro.setAlimento(alimento);
        registro.setFecha(dto.fecha());
        registro.setTipoComida(dto.tipoComida());
        registro.setCantidad(dto.cantidad());

        return toDto(registroComidaRepository.save(registro));
    }

    @Transactional
    public void delete(Long id) {
        if (!registroComidaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Registro no encontrado");
        }
        registroComidaRepository.deleteById(id);
    }

    // US 12: Ver resumen diario
    @Transactional(readOnly = true)
    public ResumenDiarioDTO getResumenDiario(Long usuarioId, LocalDate fecha) {
        var usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Verificar si tiene registros
        boolean tieneRegistros = registroComidaRepository.existsByUsuarioIdAndFecha(usuarioId, fecha);

        if (!tieneRegistros) {
            return ResumenDiarioDTO.builder()
                    .usuarioId(usuarioId)
                    .nombreUsuario(usuario.getNombre())
                    .fecha(fecha)
                    .caloriasTotales(0)
                    .caloriasPorTipoComida(new HashMap<>())
                    .caloriasPorCategoria(new HashMap<>())
                    .registros(List.of())
                    .tieneRegistros(false)
                    .mensaje("Sin datos registrados")
                    .build();
        }

        // Obtener registros
        List<RegistroComidaResponseDTO> registros = findByUsuarioIdAndFecha(usuarioId, fecha);

        // Calcular calorías totales
        Integer caloriasTotales = registroComidaRepository.calcularCaloriasTotalesDia(usuarioId, fecha);
        if (caloriasTotales == null) caloriasTotales = 0;

        // Calcular calorías por tipo de comida
        Map<String, Integer> caloriasPorTipoComida = new HashMap<>();
        List<Object[]> resultadosTipoComida = registroComidaRepository.calcularCaloriasPorTipoComida(usuarioId, fecha);
        for (Object[] resultado : resultadosTipoComida) {
            Integer tipoComida = (Integer) resultado[0];
            Long calorias = (Long) resultado[1];
            caloriasPorTipoComida.put(getTipoComidaNombre(tipoComida), calorias.intValue());
        }

        // Calcular calorías por categoría
        Map<String, Integer> caloriasPorCategoria = new HashMap<>();
        List<Object[]> resultadosCategoria = registroComidaRepository.calcularCaloriasPorCategoria(usuarioId, fecha);
        for (Object[] resultado : resultadosCategoria) {
            String categoria = (String) resultado[0];
            Long calorias = (Long) resultado[1];
            caloriasPorCategoria.put(categoria, calorias.intValue());
        }

        return ResumenDiarioDTO.builder()
                .usuarioId(usuarioId)
                .nombreUsuario(usuario.getNombre())
                .fecha(fecha)
                .caloriasTotales(caloriasTotales)
                .caloriasPorTipoComida(caloriasPorTipoComida)
                .caloriasPorCategoria(caloriasPorCategoria)
                .registros(registros)
                .tieneRegistros(true)
                .mensaje("Resumen generado exitosamente")
                .build();
    }

    private RegistroComidaResponseDTO toDto(RegistroComida registro) {
        return RegistroComidaResponseDTO.builder()
                .id(registro.getId())
                .usuarioId(registro.getUsuario().getId_usuario())
                .nombreUsuario(registro.getUsuario().getNombre())
                .alimentoId(registro.getAlimento().getId())
                .alimentoNombre(registro.getAlimento().getName())
                .categoria(registro.getAlimento().getCategoria())
                .fecha(registro.getFecha())
                .tipoComida(registro.getTipoComida())
                .tipoComidaNombre(getTipoComidaNombre(registro.getTipoComida()))
                .cantidad(registro.getCantidad())
                .caloriasConsumidas(registro.getCaloriasConsumidas())
                .fechaRegistro(registro.getFechaRegistro())
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