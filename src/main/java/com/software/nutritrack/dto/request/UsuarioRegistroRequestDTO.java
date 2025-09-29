package com.software.nutritrack.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

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
        String name
) {}