package kafoor.users.user_service.repositories;

import kafoor.users.user_service.models.Role;
import kafoor.users.user_service.models.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {
    public Optional<Role> findByName(UserRoles name);
    public boolean existsByName(UserRoles name);
}
