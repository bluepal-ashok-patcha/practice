package com.example.config;

import com.example.entities.Role;
import com.example.entities.RoleName;
import com.example.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (roleRepository.findByName(RoleName.USER).isEmpty()) {
            roleRepository.save(new Role(RoleName.USER));
        }
        if (roleRepository.findByName(RoleName.ADMIN).isEmpty()) {
            roleRepository.save(new Role(RoleName.ADMIN));
        }
    }
}
