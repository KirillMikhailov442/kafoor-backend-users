package kafoor.users.user_service.repositories;

import kafoor.users.user_service.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Long> {
    public List<Token> findAllByUserId(long userId);
    public Optional<Token> findByRefreshToken(String refresh);
}
