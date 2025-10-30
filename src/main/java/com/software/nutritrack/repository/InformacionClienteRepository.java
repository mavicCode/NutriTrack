package com.software.nutritrack.repository;

import com.software.nutritrack.model.InformacionCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InformacionClienteRepository extends JpaRepository<InformacionCliente, Long> {

    // 🔹 Obtener toda la información registrada de un cliente
    List<InformacionCliente> findByClienteId(UUID idCliente);

    // 🔹 Obtener la información más reciente del cliente (último registro)
    Optional<InformacionCliente> findTopByClienteIdOrderByFechaRegistroDesc(UUID idCliente);

    // 🔹 Verificar si ya existen registros de información para un cliente
    boolean existsByClienteId(UUID idCliente);

    // 🔹 (Opcional) Obtener información por nivel de actividad
    List<InformacionCliente> findByNivelActividad(String nivelActividad);
}