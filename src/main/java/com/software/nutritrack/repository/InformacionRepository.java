package com.software.nutritrack.repository;

import com.software.nutritrack.model.Informacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InformacionRepository extends JpaRepository<Informacion, Long> {

    @Query("SELECT i FROM Informacion i WHERE i.idUsuario = :idUsuario ORDER BY i.fechaGeneracion DESC")
    List<Informacion> findByUsuarioId(@Param("idUsuario") Long idUsuario);
}