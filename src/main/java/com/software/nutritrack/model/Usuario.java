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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Rol role;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false, updatable = false)
    private LocalDate fecha_registro;

    private LocalDate fecha_actualizacion;

    @PrePersist
    public void prePersist() {
        this.fecha_registro = this.fecha_actualizacion = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.fecha_actualizacion = LocalDate.now();
    }
}