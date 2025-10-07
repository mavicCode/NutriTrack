package com.software.nutritrack.repository;

import com.software.nutritrack.model.EstadoMeta;
import com.software.nutritrack.model.Meta;
import com.software.nutritrack.model.TipoMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetaRepository extends JpaRepository<Meta,Long> {

    //Para obtener todas las metas de un usuario

    //Para obtener una meta por su tipo

    //Verificar si existe una meta con ese tipo

    //Para obtener una meta por su estado
}
