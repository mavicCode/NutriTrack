package com.software.nutritrack.dto.response;

import com.software.nutritrack.model.enums.ObjetivoGeneral;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ClienteResponseDTO {
    private UUID id;
    private String nombre;
    private LocalDate fechaInicio;
    private ObjetivoGeneral objetivoGeneral;
    private UUID idUsuario;
}
