package com.software.nutritrack.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Float peso;

    @Column(nullable = false)
    private Float altura;

    @Column(nullable = false, updatable = false)
    private LocalDate fecha_registro;

    private LocalDate fecha_actualizacion;

    @PrePersist
    public void prePersist() {
        fecha_registro = LocalDate.now();
        fecha_actualizacion = LocalDate.now();
    }
}