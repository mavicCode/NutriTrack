package com.software.nutritrack.config;

import com.software.nutritrack.model.Cliente;
import com.software.nutritrack.model.Rol;
import com.software.nutritrack.model.enums.TipoRol;
import com.software.nutritrack.model.Usuario;
import com.software.nutritrack.repository.ClienteRepository;
import com.software.nutritrack.repository.RolRepository;
import com.software.nutritrack.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final RolRepository roleRepository;
    private final UsuarioRepository userRepository;
    private final ClienteRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing database with default data...");

        // Crear roles
        Rol userRole = createRoleIfNotExists(TipoRol.ROLE_USER);
        Rol adminRole = createRoleIfNotExists(TipoRol.ROLE_ADMIN);

        // Crear usuario admin
        createAdminUserIfNotExists(adminRole);

        log.info("Database initialization completed.");
    }

    private Rol createRoleIfNotExists(TipoRol roleType) {
        return roleRepository.findByName(roleType)
                .orElseGet(() -> {
                    Rol role = new Rol(roleType);
                    roleRepository.save(role);
                    log.info("Created role: {}", roleType);
                    return role;
                });
    }

    private void createAdminUserIfNotExists(Rol adminRole) {
        String adminEmail = "admin@fintech.com";

        if (userRepository.existsByEmail(adminEmail)) {
            log.info("Admin user already exists: {}", adminEmail);
            return;
        }

        Usuario adminUser = new Usuario();
        adminUser.setEmail(adminEmail);
        adminUser.setPassword(passwordEncoder.encode("admin123"));
        adminUser.setRol(adminRole);
        adminUser.setActive(true);
        Usuario savedAdmin = userRepository.save(adminUser);

        Cliente adminCustomer = new Cliente();
        adminCustomer.setUsuario(savedAdmin);
        adminCustomer.setName("System Administrator");
        customerRepository.save(adminCustomer);

        log.info("========================================");
        log.info("DEFAULT ADMIN USER CREATED:");
        log.info("Email: {}", adminEmail);
        log.info("Password: admin123");
        log.info("⚠️  CHANGE THIS PASSWORD IN PRODUCTION!");
        log.info("========================================");
    }
}