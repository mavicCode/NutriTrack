package com.software.nutritrack.service;
import com.software.nutritrack.dto.response.UsuarioPerfilResponseDTO;
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
public class UsuarioService {
    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    //private final EmailService emailService; // implementar envío de correos

    @Transactional(readOnly = true)
    public UsuarioPerfilResponseDTO getProfile(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        return new UsuarioPerfilResponseDTO(
                user.getId_usuario(),
                user.getEmail()
        );
    }

    @Transactional
    public void changePassword(Long userId, String currentPassword, String newPassword) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new BusinessRuleException("La contraseña actual no es válida");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Enviar correo de confirmación
        //emailService.sendPasswordChangedEmail(user.getEmail());
    }
}
