package com.software.nutritrack.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "foods")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Alimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int calorias;

    @Column(nullable = false)
    private double peso;

    @Column(nullable = false)
    private String categoria;
}