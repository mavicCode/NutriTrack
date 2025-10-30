package com.software.nutritrack.service;

import com.software.nutritrack.dto.request.LoginRequestDTO;
import com.software.nutritrack.dto.request.UsuarioRegistroRequestDTO;
import com.software.nutritrack.dto.response.AuthResponse;
import com.software.nutritrack.exception.BusinessRuleException;
import com.software.nutritrack.model.Cliente;
import com.software.nutritrack.model.Rol;
import com.software.nutritrack.model.enums.TipoRol;
import com.software.nutritrack.model.Usuario;
import com.software.nutritrack.repository.ClienteRepository;
import com.software.nutritrack.repository.RolRepository;
import com.software.nutritrack.repository.UsuarioRepository;
import com.software.nutritrack.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository userRepository;
    private final RolRepository roleRepository;
    private final ClienteRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(UsuarioRegistroRequestDTO request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessRuleException("Email already registered");
        }

        // Crear User
        Usuario user = new Usuario();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        Rol userRole = roleRepository.findByName(TipoRol.ROLE_USER)
                .orElseThrow(() -> new BusinessRuleException("Role ROLE_USER not found"));
        user.setRol(userRole);

        Usuario savedUser = userRepository.save(user);

        // Crear Customer asociado
        Cliente customer = new Cliente();
        customer.setUsuario(savedUser);
        customer.setName(request.name());
        Cliente savedCustomer = customerRepository.save(customer);

        // Generar JWT con email, nombre y customerId
        String token = jwtUtil.generateToken(
                savedUser.getEmail(),
                savedCustomer.getName(),
                savedCustomer.getId()
        );

        return new AuthResponse(token, savedUser.getEmail(), savedCustomer.getName());
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        Usuario user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Cliente customer = customerRepository.findByUsuario_Id(user.getId())
                .orElseThrow(() -> new RuntimeException("Customer not found for user"));

        // Generar JWT con email, nombre y customerId
        String token = jwtUtil.generateToken(
                user.getEmail(),
                customer.getName(),
                customer.getId()
        );

        return new AuthResponse(token, user.getEmail(), customer.getName());
    }
}