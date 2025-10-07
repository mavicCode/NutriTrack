package com.software.nutritrack.repository;

import com.software.nutritrack.model.Progreso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgresoRepository extends JpaRepository<Progreso, Long> {
}
