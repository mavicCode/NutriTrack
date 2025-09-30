package com.software.nutritrack.controller;

import com.software.nutritrack.dto.request.LoginRequestDTO;
import com.software.nutritrack.dto.request.UsuarioRegistroRequestDTO;
import com.software.nutritrack.dto.response.LoginResponseDTO;
import com.software.nutritrack.dto.response.UsuarioRegistroResponseDTO;
import com.software.nutritrack.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UsuarioRegistroResponseDTO> register(@Valid @RequestBody UsuarioRegistroRequestDTO dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}