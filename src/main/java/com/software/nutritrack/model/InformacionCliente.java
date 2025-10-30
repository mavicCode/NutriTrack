package com.software.nutritrack.model;
import com.software.nutritrack.model.enums.Sexo;
import com.software.nutritrack.model.enums.NivelActividad;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "informacion_clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InformacionCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer edad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sexo sexo;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal altura; // en cm o metros, según tu lógica

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal peso; // en kg

    @Column(precision = 5, scale = 2)
    private BigDecimal imc; // índice de masa corporal (opcional calcularlo en backend)

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_actividad", nullable = false)
    private NivelActividad nivelActividad;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
}
