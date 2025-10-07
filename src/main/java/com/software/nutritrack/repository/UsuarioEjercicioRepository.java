package com.software.nutritrack.repository;

import com.software.nutritrack.model.UsuarioEjercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioEjercicioRepository extends JpaRepository<UsuarioEjercicio, Integer> {

    List<UsuarioEjercicio> findByUsuario_IdUsuario(Integer idUsuario);

    List<UsuarioEjercicio> findByEjercicio_IdEjercicio(Long ejercicioIdEjercicio);
}