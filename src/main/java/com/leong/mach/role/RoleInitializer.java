package com.leong.mach.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner{
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        List<String> roles = List.of(
            "USER", "ADMIN"
        );
        for (String role : roles) {
            if (roleRepository.findByName(role).isEmpty()) { // ! chạy lệnh nếu không có role nào là USER
				roleRepository.save(Role.builder().name(role).build()); // ! thì save 1 đối tượng name USER
			}
        }
    }
}
