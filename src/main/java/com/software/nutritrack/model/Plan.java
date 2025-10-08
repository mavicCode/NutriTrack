package com.software.nutritrack.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plan")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plan")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "tipo_comida", nullable = false)
    private Integer tipoComida; // 1: Desayuno, 2: Almuerzo, 3: Cena, 4: Snack

    @Column(nullable = false, updatable = false)
    private LocalDate fecha;

    @Column(length = 400)
    private String descripcion;

    @Column(name = "clasificacion")
    private Integer clasificacion;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ComidaAlimento> comidas = new ArrayList<>();

}