package com.software.nutritrack.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "excercises")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class Ejercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_ejercicio;

    @Column(nullable = false, unique = true)
    private String descripcion;

    @Column(nullable = false)
    private String categoria;

    private LocalDate fecha_actualizacion;

    @PrePersist
    public void prePersist() {
        fecha_actualizacion = LocalDate.now();
    }
}