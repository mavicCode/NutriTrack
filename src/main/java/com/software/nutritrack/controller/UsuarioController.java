package com.software.nutritrack.controller;

import com.software.nutritrack.dto.response.UsuarioPerfilResponseDTO;
import com.software.nutritrack.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService userService;

    @GetMapping("/{userId}/profile")
    public ResponseEntity<UsuarioPerfilResponseDTO> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getProfile(userId));
    }

    @PutMapping("/{userId}/change-password")
    public ResponseEntity<String> changePassword(
            @PathVariable Long userId,
            @RequestParam String currentPassword,
            @RequestParam String newPassword) {
        userService.changePassword(userId, currentPassword, newPassword);
        return ResponseEntity.ok("Contraseña actualizada correctamente");
    }
}