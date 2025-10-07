package com.software.nutritrack.dto.response;

import com.software.nutritrack.model.EstadoMeta;
import com.software.nutritrack.model.TipoMeta;
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