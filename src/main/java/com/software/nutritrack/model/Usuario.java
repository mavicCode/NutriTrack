package com.software.nutritrack.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false, updatable = false)
    private LocalDate fecha_registro;

    private LocalDate fecha_actualizacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol")
    private Rol rol;

    @PrePersist
    public void prePersist() {
        this.fecha_registro = this.fecha_actualizacion = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.fecha_actualizacion = LocalDate.now();
    }
}