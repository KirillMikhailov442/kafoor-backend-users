package dev.kafoor.users.repository;

import dev.kafoor.users.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    public Optional<UserEntity> findByEmail(String email);

    public Optional<UserEntity> findByNickname(String nickname);

    public List<UserEntity> findAllByIdIn(List<Long> ids);

    public boolean existsByEmail(String email);

    public boolean existsByNickname(String nickname);
}
