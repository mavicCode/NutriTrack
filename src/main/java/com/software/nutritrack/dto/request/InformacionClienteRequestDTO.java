package com.software.nutritrack.dto.request;

import com.software.nutritrack.model.enums.Sexo;
import com.software.nutritrack.model.enums.NivelActividad;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InformacionClienteRequestDTO {
    private Integer edad;
    private Sexo sexo;
    private BigDecimal altura;
    private BigDecimal peso;
    private NivelActividad nivelActividad;
}
