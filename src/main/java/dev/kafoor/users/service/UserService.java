package dev.kafoor.users.service;

import dev.kafoor.users.entity.User;
import dev.kafoor.users.exception.NotFound;
import dev.kafoor.users.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepo userRepo;
    private final RoleService roleService;
    private final TokenService tokenService;

    public List<User> findAllUsers() {
        List<User> users = userRepo.findAll();
        if (users.isEmpty())
            throw new NotFound("Users not found");
        return users;
    }
}
