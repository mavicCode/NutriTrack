package com.software.nutritrack.repository;

import com.software.nutritrack.model.ClienteEjercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteEjercicioRepository extends JpaRepository<ClienteEjercicio, Integer> {

    List<ClienteEjercicio> findByCliente_Id(String idCliente);

    List<ClienteEjercicio> findByEjercicio_Id(Long idEJercicio);
}