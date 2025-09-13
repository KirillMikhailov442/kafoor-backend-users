package kafoor.users.user_service.services;

import kafoor.users.user_service.dtos.*;
import kafoor.users.user_service.exceptions.Conflict;
import kafoor.users.user_service.exceptions.NotFound;
import kafoor.users.user_service.models.Role;
import kafoor.users.user_service.models.User;
import kafoor.users.user_service.models.enums.Tokens;
import kafoor.users.user_service.models.enums.UserRoles;
import kafoor.users.user_service.repositories.UserRepo;
import kafoor.users.user_service.utils.jwt.JWTUtils;
import kafoor.users.user_service.utils.jwt.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleService roleService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private JWTUtils jwtUtils;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public List<User> getAllUsers(){
        List<User> users = userRepo.findAll();
        if(users.isEmpty()) throw new NotFound("Users not found");
        return users;
    }

    public User findUserById(long id){
        return userRepo.findById(id).orElseThrow(() -> new NotFound("User not found by id"));
    }

    public User findUserByNickname(String nickname){
        return userRepo.findByNickname(nickname).orElseThrow(() -> new NotFound(("User not found by nickname")));
    }

    public User findUserByEmail(String email){
        return userRepo.findByEmail(email).orElseThrow(()-> new NotFound("User not found by email"));
    }

    public void confirmUser(long id){
        User user = findUserById(id);
        user.setConfirmed(true);
        userRepo.save(user);
    }

    @Transient
    public UserCreateResDTO createUser(UserCreateReqDTO userDto, HeaderDTO headerDto){
        if(userRepo.existsByEmail(userDto.getEmail())) throw new Conflict(("A user with such email already exists"));
        if(userRepo.existsByNickname(userDto.getNickname())) throw new Conflict(("A user with such nickname already exists"));

        Set<Role> roles = Set.of(roleService.findOrCreateRole(UserRoles.USER));
        System.out.println("Роль сделана/добавлена");
        User newUser = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .nickname(userDto.getNickname())
                .isConfirmed(false)
                .roles(roles)
                .password(encoder.encode(userDto.getPassword())).build();
        User createdUser = userRepo.save(newUser);

        UserPrincipal userPrincipal = loadUserByUsername(String.valueOf(newUser.getId()));
        String tokenAccess = jwtUtils.generateToken(userPrincipal, Tokens.ACCESS_TOKEN);
        String tokenRefresh = jwtUtils.generateToken(userPrincipal, Tokens.REFRESH_TOKEN);
        TokenCreateDTO tokenCreateDTO = TokenCreateDTO.builder()
                .IP(headerDto.getIP())
                .userAgent(headerDto.getUserAgent())
                .refresh(tokenRefresh).build();
        tokenService.createToken(tokenCreateDTO, newUser);

        return UserCreateResDTO.builder()
                .user(createdUser)
                .access_token(tokenAccess)
                .refresh_token(tokenRefresh).build();
    }

    public User updateUser(UserUpdateDTO dto, long id){
        User user = findUserById(id);
        if(userRepo.existsByEmail(dto.getEmail()) && user.getId() != id){
            throw new Conflict("A user with such email already exists");
        }
        if(userRepo.existsByNickname(dto.getNickname()) && user.getId() != id){
            throw new Conflict("A user with such nickname already exists");
        }
        if(dto.getName() != null && !dto.getName().isEmpty()) user.setName(dto.getName());
        if(dto.getEmail() != null && !dto.getEmail().isEmpty()) user.setEmail(dto.getEmail());
        if(dto.getNickname() != null && !dto.getNickname().isEmpty()) user.setNickname(dto.getNickname());

        return userRepo.save(user);
    }

    public void deleteUser(long id){
        userRepo.deleteById(id);
    }

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findById(Long.valueOf(username))
                .orElseThrow(()-> new UsernameNotFoundException(username));
        return new UserPrincipal(user);
    }
}
