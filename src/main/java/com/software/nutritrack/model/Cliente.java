package com.software.nutritrack.model;

import com.software.nutritrack.model.enums.ObjetivoGeneral;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Table(name = "clientes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate fecha_inicio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ObjetivoGeneral objetivo_general = ObjetivoGeneral.OTRO;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @PrePersist
    protected void onCreate() {
        this.fecha_inicio = LocalDate.now();
    }
}
