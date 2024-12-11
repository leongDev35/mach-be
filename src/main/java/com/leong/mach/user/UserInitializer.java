package com.leong.mach.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.leong.mach.role.Role;
import com.leong.mach.role.RoleRepository;

@Component
public class UserInitializer implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${application.admin.firstname}")
    private String firstname;
    @Value("${application.admin.lastname}")
    private String lastname;
    @Value("${application.admin.email}")
    private String email;
    @Value("${application.admin.password}")
    private String password;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail(email).isEmpty()) {
            Role roleUser = roleRepository.findByName("USER")
                    .orElseThrow(() -> new IllegalStateException("ROLE USER was not initiated"));
            List<Role> listRole = roleRepository.findAll();

            var roleAdmin = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new IllegalStateException("ROLE ADMIN was not initiated"));

            List<Role> userRoles = new ArrayList<>();
            userRoles.add(roleUser);
            userRoles.add(roleAdmin);
            var user = User.builder() // ! tạo 1 User object từ request
                    .firstname(firstname)
                    .lastname(lastname)
                    .email(email)
                    .password(passwordEncoder.encode(password)) // ! mã hóa password
                    .accountLocked(false)
                    .enabled(true)
                    .roles(listRole)
                    .build();
            userRepository.save(user);
        }
    }
}
