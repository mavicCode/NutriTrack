package com.software.nutritrack.dto.response;

import com.software.nutritrack.model.enums.Sexo;
import com.software.nutritrack.model.enums.NivelActividad;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class InformacionClienteResponseDTO {
    private Long id;
    private UUID idCliente;
    private Integer edad;
    private Sexo sexo;
    private BigDecimal altura;
    private BigDecimal peso;
    private BigDecimal imc;
    private NivelActividad nivelActividad;
    private LocalDate fechaRegistro;
}
