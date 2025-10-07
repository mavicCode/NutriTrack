package com.software.nutritrack.repository;

import com.software.nutritrack.model.Enums.EstadoMeta;
import com.software.nutritrack.model.Meta;
import com.software.nutritrack.model.Enums.TipoMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetaRepository extends JpaRepository<Meta,Long> {

    //Para obtener todas las metas de un usuario
    @Query("SELECT m FROM Meta m WHERE m.usuario.idUsuario = :idUsuario")
    List<Meta> findByUsuarioId(@Param("idUsuario") Long idUsuario);

    //Para obtener una meta por su tipo
    List<Meta> findByTipo(TipoMeta tipo);

    //Verificar si existe una meta con ese tipo
    boolean existsByTipo(TipoMeta tipo);

    //Para obtener una meta por su estado
    List<Meta> findByEstado(EstadoMeta estado);
}
