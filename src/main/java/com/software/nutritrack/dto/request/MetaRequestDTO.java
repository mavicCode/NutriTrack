package com.software.nutritrack.dto.request;

import com.software.nutritrack.model.enums.EstadoMeta;
import com.software.nutritrack.model.enums.TipoMeta;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class MetaRequestDTO {
    private TipoMeta tipo;
    private String descripcion;
    private EstadoMeta estado;
    private LocalDate fecha;
    private UUID idUsuario;
}