package com.software.nutritrack.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "plan_food")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComidaAlimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comida")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plan", nullable = false)
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_alimento", nullable = false)
    private Alimento alimento;

    @Column(nullable = false)
    @Builder.Default
    private Double cantidad = 1.0; // Cantidad de porciones

    // Metodo para calcular calorías totales de esta comida
    public int getCaloriasTotales() {
        return (int) (alimento.getCalorias() * cantidad);
    }
}