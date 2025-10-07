package com.software.nutritrack.dto.request;

import com.software.nutritrack.model.Enums.EstadoMeta;
import com.software.nutritrack.model.Enums.TipoMeta;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MetaRequestDTO {
    private TipoMeta tipo;
    private String descripcion;
    private EstadoMeta estado;
    private LocalDate fecha;
    private Long idUsuario;
}