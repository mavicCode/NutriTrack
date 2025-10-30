package com.software.nutritrack.dto.response;

import com.software.nutritrack.model.enums.EstadoMeta;
import com.software.nutritrack.model.enums.TipoMeta;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MetaResponseDTO {
    private Long id;
    private TipoMeta tipo;
    private String descripcion;
    private EstadoMeta estado;
    private LocalDate fecha;
}