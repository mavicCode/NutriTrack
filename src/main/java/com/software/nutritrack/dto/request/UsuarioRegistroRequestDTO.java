package com.software.nutritrack.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UsuarioRegistroRequestDTO(

        // Credenciales del usuario
        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo debe tener un formato válido")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        String password,

        // Datos comunes del perfil
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotNull(message = "El peso es obligatorio")
        Float peso,

        @NotNull(message = "La altura es obligatoria")
        Float altura,

        LocalDate fecha_registro

) {}