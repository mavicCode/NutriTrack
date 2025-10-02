package com.software.nutritrack.repository;

import com.software.nutritrack.model.Ejercicio;
import com.software.nutritrack.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EjercicioRepository extends JpaRepository<Ejercicio, Long> {

    // Buscar ejercicio por categoria
    @Query("SELECT e FROM Ejercicio e WHERE e.categoria = :categoria")
    Optional<Usuario> findByCategoria(@Param("categoria") String descripcion);

    // Verificar si existe un ejercicio con la misma descripcion
    @Query("SELECT COUNT(e) > 0 FROM Ejercicio e WHERE e.descripcion = :descripcion")
    boolean existsByDescripcion(@Param("descripcion") String email);
}