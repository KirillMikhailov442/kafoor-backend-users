package dev.kafoor.users.service;

import dev.kafoor.users.entity.RoleEntity;
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

    public List<RoleEntity> findAllRoles() {
        return roleRepo.findAll();
    }

    public Optional<RoleEntity> findRoleById(long id) {
        return roleRepo.findById(id);
    }

    public RoleEntity findRoleByIdOrThrow(long id) {
        return roleRepo.findById(id).orElseThrow(() -> new NotFound("role not found by id " + id));
    }

    public Optional<RoleEntity> findRoleByName(UserRole name) {
        return roleRepo.findByName(name);
    }

    public RoleEntity findRoleByNameOrThrow(UserRole name) {
        return roleRepo.findByName(name).orElseThrow(() -> new NotFound("role not found by name " + name));
    }

    public RoleEntity createRole(UserRole name) {
        if (roleRepo.existsByName(name)) {
            throw new Conflict(String.format("role %s already exists in the database", name));
        }
        RoleEntity newRole = RoleEntity.builder().name(name).build();
        roleRepo.save(newRole);
        return newRole;
    }

    public RoleEntity updateRole(UserRole newName, long id) {
        RoleEntity role = findRoleByIdOrThrow(id);
        role.setName(newName);
        return roleRepo.save(role);
    }

    public RoleEntity findOrCreateRole(UserRole name) {
        Optional<RoleEntity> role = findRoleByName(name);
        return role.orElseGet(() -> createRole(name));
    }

    public void deleteRoleById(long id) {
        roleRepo.delete(findRoleByIdOrThrow(id));
    }

    public void deleteRoleByName(UserRole name) {
        roleRepo.delete(findRoleByNameOrThrow(name));
    }
}