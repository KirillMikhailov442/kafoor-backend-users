package dev.kafoor.users.repository;

import dev.kafoor.users.entity.RoleEntity;
import dev.kafoor.users.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<RoleEntity, Long> {
    public Optional<RoleEntity> findByName(UserRole name);

    public boolean existsByName(UserRole name);
}