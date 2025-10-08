package com.software.nutritrack.model.Ids;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteEjercicioId implements Serializable {
    private String idCliente;
    private Long idEjercicio;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClienteEjercicioId that = (ClienteEjercicioId) o;
        return Objects.equals(idCliente, that.idCliente) && Objects.equals(idEjercicio, that.idEjercicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCliente, idEjercicio);
    }
}
