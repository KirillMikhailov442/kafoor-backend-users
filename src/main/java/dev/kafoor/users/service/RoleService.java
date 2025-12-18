package dev.kafoor.users.service;

import dev.kafoor.users.entity.Role;
import dev.kafoor.users.entity.enums.UserRole;
import dev.kafoor.users.exception.Conflict;
import dev.kafoor.users.exception.NotFound;
import dev.kafoor.users.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoleService {
    private final RoleRepo roleRepo;

    public List<Role> findAllRoles() {
        return roleRepo.findAll();
    }

    public Optional<Role> findRoleById(long id) {
        return roleRepo.findById(id);
    }

    public Role findRoleByIdOrThrow(long id) {
        return roleRepo.findById(id).orElseThrow(() -> new NotFound("Role not found by id " + id));
    }

    public Optional<Role> findRoleByName(UserRole name) {
        return roleRepo.findByName(name);
    }

    public Role findRoleByNameOrThrow(UserRole name) {
        return roleRepo.findByName(name).orElseThrow(() -> new NotFound("Role not found by name " + name));
    }

    public Role createRole(UserRole name) {
        if (roleRepo.existsByName(name)) {
            throw new Conflict(String.format("Role %s already exists in the database", name));
        }
        Role newRole = Role.builder().name(name).build();
        roleRepo.save(newRole);
        return newRole;
    }

    public Role updateRole(UserRole newName, long id) {
        Role role = findRoleByIdOrThrow(id);
        role.setName(newName);
        return roleRepo.save(role);
    }

    public Role findOrCreateRole(UserRole name) {
        Optional<Role> role = findRoleByName(name);
        return role.orElseGet(() -> createRole(name));
    }

    public void deleteRoleById(long id) {
        roleRepo.delete(findRoleByIdOrThrow(id));
    }

    public void deleteRoleByName(UserRole name) {
        roleRepo.delete(findRoleByNameOrThrow(name));
    }
}