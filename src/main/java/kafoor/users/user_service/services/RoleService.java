package kafoor.users.user_service.services;

import kafoor.users.user_service.exceptions.Conflict;
import kafoor.users.user_service.exceptions.NotFound;
import kafoor.users.user_service.models.Role;
import kafoor.users.user_service.models.enums.UserRoles;
import kafoor.users.user_service.repositories.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepo roleRepo;

    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }

    public Role findRoleById(long id) {
        return roleRepo.findById(id).orElseThrow(() -> new NotFound("Role not found by id"));
    }

    public Role findRoleByName(UserRoles name) {
        return roleRepo.findByName(name).orElseThrow(() -> new NotFound("Role not found by name"));
    }

    public Role findOrCreateRole(UserRoles name) {
        Optional<Role> role = roleRepo.findByName(name);
        return role.orElseGet(() -> createRole(name));
    }

    public Role createRole(UserRoles name) {
        if (roleRepo.existsByName(name))
            throw new Conflict(String.format("Role %s already exists in the database", name));
        Role newRole = Role.builder().name(name).build();
        roleRepo.save(newRole);
        return newRole;
    }

    public Role updateRole(UserRoles newName, long id) {
        Role role = findRoleById(id);
        role.setName(newName);
        return roleRepo.save(role);
    }
}
