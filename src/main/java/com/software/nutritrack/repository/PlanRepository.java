package com.software.nutritrack.repository;

import com.software.nutritrack.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    // Buscar planes por usuario
    @Query("SELECT p FROM Plan p WHERE p.usuario.id_usuario = :usuarioId")
    List<Plan> findByUsuarioId(@Param("usuarioId") Long usuarioId);

    // Buscar planes por usuario y tipo de comida
    @Query("SELECT p FROM Plan p WHERE p.usuario.id_usuario = :usuarioId AND p.tipoComida = :tipoComida")
    List<Plan> findByUsuarioIdAndTipoComida(@Param("usuarioId") Long usuarioId,
                                            @Param("tipoComida") Integer tipoComida);

    // Buscar planes por clasificación
    @Query("SELECT p FROM Plan p WHERE p.clasificacion = :clasificacion")
    List<Plan> findByClasificacion(@Param("clasificacion") Integer clasificacion);

    // Verificar si existe un plan para un usuario
    @Query("SELECT COUNT(p) > 0 FROM Plan p WHERE p.usuario.id_usuario = :usuarioId")
    boolean existsByUsuarioId(@Param("usuarioId") Long usuarioId);

    // Buscar planes con sus comidas (JOIN FETCH para evitar N+1)
    @Query("SELECT DISTINCT p FROM Plan p LEFT JOIN FETCH p.comidas WHERE p.usuario.id_usuario = :usuarioId")
    List<Plan> findByUsuarioIdWithComidas(@Param("usuarioId") Long usuarioId);
}