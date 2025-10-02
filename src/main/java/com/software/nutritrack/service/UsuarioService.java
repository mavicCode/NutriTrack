package com.software.nutritrack.service;
import com.software.nutritrack.dto.request.UsuarioUpdateRequestDTO;
import com.software.nutritrack.dto.response.UsuarioPerfilResponseDTO;
import com.software.nutritrack.exception.BusinessRuleException;
import com.software.nutritrack.exception.ResourceNotFoundException;
import com.software.nutritrack.model.Usuario;
import com.software.nutritrack.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

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
                user.getEmail(),
                user.getNombre(),
                user.getPeso(),
                user.getAltura(),
                user.getFecha_registro(),
                user.getFecha_actualizacion()
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

    public UsuarioPerfilResponseDTO updateProfile(Long userId, UsuarioUpdateRequestDTO request) {
        Usuario usuario = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        if (request.nombre() != null && !request.nombre().isEmpty()) {
            usuario.setNombre(request.nombre());
        }
        usuario.setPeso(request.peso());
        usuario.setAltura(request.altura());
        usuario.setFecha_actualizacion(LocalDate.now());

        userRepository.save(usuario);

        return new UsuarioPerfilResponseDTO(usuario.getId_usuario(), usuario.getEmail(), usuario.getNombre(), usuario.getPeso(),
                usuario.getAltura(), usuario.getFecha_registro(), usuario.getFecha_actualizacion());
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        userRepository.deleteById(userId);
    }

}
