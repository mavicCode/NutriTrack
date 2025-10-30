package com.software.nutritrack.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "informacion")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Informacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInformacion;

    @Column(nullable = false)
    private UUID idUsuario;

    @Column(nullable = false)
    private LocalDate fechaGeneracion;

    @Column(nullable = false, length = 50)
    private String formato;

    @Column(nullable = false)
    private String rutaArchivo;

    @PrePersist
    public void prePersist() {
        fechaGeneracion = LocalDate.now();
    }
}