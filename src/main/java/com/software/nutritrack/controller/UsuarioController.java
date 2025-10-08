package com.software.nutritrack.controller;

import com.software.nutritrack.dto.request.UsuarioUpdateRequestDTO;
import com.software.nutritrack.dto.response.UsuarioPerfilResponseDTO;
import com.software.nutritrack.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService userService;

    @GetMapping("/{userId}/profile")
    public ResponseEntity<UsuarioPerfilResponseDTO> getProfile(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getProfile(userId));
    }

    @PutMapping("/{userId}/change-password")
    public ResponseEntity<String> changePassword(
            @PathVariable String userId,
            @RequestParam String currentPassword,
            @RequestParam String newPassword) {
        userService.changePassword(userId, currentPassword, newPassword);
        return ResponseEntity.ok("Contraseña actualizada correctamente");
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UsuarioPerfilResponseDTO> updateProfile(
            @PathVariable String userId,
            @RequestBody UsuarioUpdateRequestDTO request
    ) {
        UsuarioPerfilResponseDTO updated = userService.updateProfile(userId, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, String>> deleteAccount(
            @PathVariable String userId
    ) {

        userService.deleteUser(userId);
        return ResponseEntity.ok(Map.of("mensaje", "Cuenta eliminada correctamente"));
    }

}