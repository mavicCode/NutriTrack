package com.software.nutritrack.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "food_record")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistroComida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    private Alimento alimento;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "tipo_comida", nullable = false)
    private Integer tipoComida; // 1: Desayuno, 2: Almuerzo, 3: Cena, 4: Snack

    @Column(nullable = false)
    @Builder.Default
    private Double cantidad = 1.0; // Porciones consumidas

    @Column(name = "calorias_consumidas", nullable = false)
    private Integer caloriasConsumidas; // Calorías totales (calculado)

    @Column(name = "fecha_registro")
    @Builder.Default
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    // Metodo para calcular calorías antes de guardar
    @PrePersist
    @PreUpdate
    public void calcularCalorias() {
        if (alimento != null && cantidad != null) {
            this.caloriasConsumidas = (int) (alimento.getCalorias() * cantidad);
        }
    }
}