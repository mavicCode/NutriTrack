package com.software.nutritrack.repository;

import com.software.nutritrack.model.Rol;
import com.software.nutritrack.model.Enums.TipoRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByName(TipoRol name);
}
