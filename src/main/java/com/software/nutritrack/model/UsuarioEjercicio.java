package com.software.nutritrack.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "usuario_ejercicio")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioEjercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_usuario_ejercicio;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_ejercicio", nullable = false)
    private Ejercicio ejercicio;

    @Column(nullable = false)
    private LocalDate fecha;

    /** Indica si el ejercicio fue completado o está pendiente */
    @Column(nullable = false)
    private Boolean estado;

    /** Duración del ejercicio en minutos */
    @Column(nullable = true)
    private Integer duracion;

    /** Número de series o repeticiones (para rutinas de fuerza) */
    @Column(nullable = true)
    private Integer repeticiones;

    /** Calorías estimadas quemadas durante el ejercicio */
    @Column(nullable = true)
    private Double calorias_quemadas;

    /** Campo opcional para notas o comentarios del usuario */
    @Column(length = 300)
    private String observaciones;
}
