package com.software.nutritrack.model;

import com.software.nutritrack.model.enums.TipoRol;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private TipoRol name;

    public Rol(TipoRol name) {
        this.name = name;
    }
}