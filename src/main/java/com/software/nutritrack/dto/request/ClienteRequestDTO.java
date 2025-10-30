package com.software.nutritrack.dto.request;

import com.software.nutritrack.model.enums.ObjetivoGeneral;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClienteRequestDTO {

    @NotBlank
    private String nombre;

    @NotNull
    private ObjetivoGeneral objetivoGeneral;
}
