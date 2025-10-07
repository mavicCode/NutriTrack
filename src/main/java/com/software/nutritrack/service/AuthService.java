package com.software.nutritrack.service;

import com.software.nutritrack.dto.request.LoginRequestDTO;
import com.software.nutritrack.dto.request.UsuarioRegistroRequestDTO;
import com.software.nutritrack.dto.response.LoginResponseDTO;
import com.software.nutritrack.dto.response.UsuarioRegistroResponseDTO;
import com.software.nutritrack.exception.BusinessRuleException;
import com.software.nutritrack.exception.ResourceNotFoundException;
import com.software.nutritrack.model.Usuario;
import com.software.nutritrack.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioRegistroResponseDTO register(UsuarioRegistroRequestDTO dto) {
        // Regla: no permitir emails duplicados
        if (userRepository.existsByEmail(dto.email())) {
            throw new BusinessRuleException("El email ya está registrado");
        }

        // Crear usuario base
        var user = Usuario.builder()
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .nombre(dto.nombre())
                .altura(dto.altura())
                .peso(dto.peso())
                .build();

        var savedUser = userRepository.save(user);

        Long profileId = null;

        // Respuesta
        return UsuarioRegistroResponseDTO.builder()
                .userId(savedUser.getIdUsuario())
                .email(savedUser.getEmail())
                .profileId(profileId)
                .nombre(dto.nombre())
                .altura(dto.altura())
                .peso(dto.peso())
                .build();

    }

    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO dto) {
        var user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Validar credenciales
        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new BusinessRuleException("Credenciales inválidas");
        }

        // TODO: aquí generar JWT real; por ahora token simulado
        String fakeToken = "demo-token";

        return LoginResponseDTO.builder()
                .userId(user.getIdUsuario())
                .email(user.getEmail())
                .token(fakeToken)
                .build();
    }
}