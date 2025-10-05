package com.software.nutritrack.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "progresos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Progreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private boolean cumplido;//Si o No

    @Column(nullable = false)
    private double porcentaje;

    @Column(length = 255, nullable = false)
    private String recomendaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_meta", nullable = false)
    private Meta meta;

}
