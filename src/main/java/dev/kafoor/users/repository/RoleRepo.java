package dev.kafoor.users.repository;

import dev.kafoor.users.entity.Role;
import dev.kafoor.users.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {
    public Optional<Role> findByName(UserRole name);

    public boolean existsByName(UserRole name);
}