package com.software.nutritrack.model;

import com.software.nutritrack.model.Enums.EstadoMeta;
import com.software.nutritrack.model.Enums.TipoMeta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "goals")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Meta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoMeta tipo;//Nutricion, hidratacion, ejercicio, personalizada

    @Column(length = 200, nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMeta estado;//Activo, No_activo

    @Column(nullable = false)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "meta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Progreso> progresos = new HashSet<>();

}

