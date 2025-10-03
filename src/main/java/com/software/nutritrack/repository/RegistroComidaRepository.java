package com.software.nutritrack.repository;

import com.software.nutritrack.model.RegistroComida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RegistroComidaRepository extends JpaRepository<RegistroComida, Long> {

    // Buscar registros por usuario y fecha (US 09, 12)
    @Query("SELECT r FROM RegistroComida r WHERE r.usuario.id_usuario = :usuarioId AND r.fecha = :fecha")
    List<RegistroComida> findByUsuarioIdAndFecha(@Param("usuarioId") Long usuarioId,
                                                 @Param("fecha") LocalDate fecha);

    // Buscar registros por usuario, fecha y tipo de comida
    @Query("SELECT r FROM RegistroComida r WHERE r.usuario.id_usuario = :usuarioId AND r.fecha = :fecha AND r.tipoComida = :tipoComida")
    List<RegistroComida> findByUsuarioIdAndFechaAndTipoComida(@Param("usuarioId") Long usuarioId,
                                                              @Param("fecha") LocalDate fecha,
                                                              @Param("tipoComida") Integer tipoComida);

    // Buscar registros por usuario en un rango de fechas
    @Query("SELECT r FROM RegistroComida r WHERE r.usuario.id_usuario = :usuarioId AND r.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<RegistroComida> findByUsuarioIdAndFechaBetween(@Param("usuarioId") Long usuarioId,
                                                        @Param("fechaInicio") LocalDate fechaInicio,
                                                        @Param("fechaFin") LocalDate fechaFin);

    // Calcular calorías totales por día (US 12)
    @Query("SELECT SUM(r.caloriasConsumidas) FROM RegistroComida r WHERE r.usuario.id_usuario = :usuarioId AND r.fecha = :fecha")
    Integer calcularCaloriasTotalesDia(@Param("usuarioId") Long usuarioId,
                                       @Param("fecha") LocalDate fecha);

    // Calcular calorías por tipo de comida en un día (US 12)
    @Query("SELECT r.tipoComida, SUM(r.caloriasConsumidas) FROM RegistroComida r WHERE r.usuario.id_usuario = :usuarioId AND r.fecha = :fecha GROUP BY r.tipoComida")
    List<Object[]> calcularCaloriasPorTipoComida(@Param("usuarioId") Long usuarioId,
                                                 @Param("fecha") LocalDate fecha);

    // Calcular calorías por categoría en un día (US 12)
    @Query("SELECT r.alimento.categoria, SUM(r.caloriasConsumidas) FROM RegistroComida r WHERE r.usuario.id_usuario = :usuarioId AND r.fecha = :fecha GROUP BY r.alimento.categoria")
    List<Object[]> calcularCaloriasPorCategoria(@Param("usuarioId") Long usuarioId,
                                                @Param("fecha") LocalDate fecha);

    // Verificar si hay registros para una fecha
    @Query("SELECT COUNT(r) > 0 FROM RegistroComida r WHERE r.usuario.id_usuario = :usuarioId AND r.fecha = :fecha")
    boolean existsByUsuarioIdAndFecha(@Param("usuarioId") Long usuarioId,
                                      @Param("fecha") LocalDate fecha);

    // Eliminar todos los registros de un día
    @Query("DELETE FROM RegistroComida r WHERE r.usuario.id_usuario = :usuarioId AND r.fecha = :fecha")
    void deleteByUsuarioIdAndFecha(@Param("usuarioId") Long usuarioId,
                                   @Param("fecha") LocalDate fecha);
}