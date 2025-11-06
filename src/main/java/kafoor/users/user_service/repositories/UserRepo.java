package kafoor.users.user_service.repositories;

import kafoor.users.user_service.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);

    public Optional<User> findByNickname(String nickname);

    public boolean existsByEmail(String email);

    public boolean existsByNickname(String nickname);

    public List<User> findAllByIdIn(List<Long> ids);
}
