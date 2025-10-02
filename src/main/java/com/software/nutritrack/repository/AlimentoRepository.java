package com.software.nutritrack.repository;

import com.software.nutritrack.model.Alimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlimentoRepository extends JpaRepository<Alimento, Long> {

    // Buscar alimento por nombre
    @Query("SELECT a FROM Alimento a WHERE LOWER(a.name) = LOWER(:name)")
    Optional<Alimento> findByName(@Param("nombre") String name);

    // Buscar alimentos por categoría
    @Query("SELECT a FROM Alimento a WHERE a.categoria = :categoria")
    List<Alimento> findByCategoria(@Param("categoria") String categoria);

    // Buscar alimentos que contengan un texto en el nombre
    @Query("SELECT a FROM Alimento a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Alimento> findByNameContaining(@Param("nombre") String name);

    // Verificar si existe un alimento con ese nombre
    @Query("SELECT COUNT(a) > 0 FROM Alimento a WHERE LOWER(a.name) = LOWER(:name)")
    boolean existsByName(@Param("nombre") String name);

    // Buscar todas las categorías distintas
    @Query("SELECT DISTINCT a.categoria FROM Alimento a ORDER BY a.categoria")
    List<String> findDistinctCategorias();
}