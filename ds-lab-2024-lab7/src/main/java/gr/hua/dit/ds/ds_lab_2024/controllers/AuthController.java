package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.Role;
import gr.hua.dit.ds.ds_lab_2024.entities.User;
import gr.hua.dit.ds.ds_lab_2024.repositories.RoleRepository;
import gr.hua.dit.ds.ds_lab_2024.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;

@Controller
public class AuthController {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(RoleRepository roleRepository,
                          UserRepository userRepository,
                          BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void setup() {
        // Create default roles
        Role roleTenant = new Role("ROLE_TENANT");
        Role roleOwner = new Role("ROLE_OWNER");
        Role roleAdmin = new Role("ROLE_ADMIN");

        roleRepository.updateOrInsert(roleTenant);
        roleRepository.updateOrInsert(roleOwner);
        roleRepository.updateOrInsert(roleAdmin);

        // Create a default admin if one doesn't exist.
        if (!userRepository.existsByUsername("admin")) {
            // Create a new user (or Admin) instance.
            User admin = new User("admin1", "admin1@mail.com", passwordEncoder.encode("123"));
            // Assign the ROLE_ADMIN to this user.
            admin.setSecurityRole("ROLE_ADMIN");
            userRepository.save(admin);
        }
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
}