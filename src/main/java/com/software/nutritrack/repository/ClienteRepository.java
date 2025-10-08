package com.software.nutritrack.repository;

import com.software.nutritrack.model.Enums.KycStatus;
import com.software.nutritrack.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {
    Optional<Cliente> findByUsuario_Id(String usuarioId);

    // Para reportes
    long countByKycStatus(KycStatus kycStatus);
    long countByActive(Boolean active);
}
