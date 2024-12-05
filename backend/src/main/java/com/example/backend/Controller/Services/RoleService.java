package com.example.backend.Controller.Services;

import com.example.backend.Model.Entities.Role;
import com.example.backend.Model.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElse(null); // Devuelve el rol o null si no se encuentra
    }
}
