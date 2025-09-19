package kafoor.users.user_service.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import kafoor.users.user_service.dtos.*;
import kafoor.users.user_service.models.Role;
import kafoor.users.user_service.models.Token;
import kafoor.users.user_service.models.User;
import kafoor.users.user_service.models.enums.UserRoles;
import kafoor.users.user_service.services.RoleService;
import kafoor.users.user_service.services.TokenService;
import kafoor.users.user_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.util.List;

@Tag(name = "GraphQL", description = "GraphQL operations")
@Controller
public class GraphqlController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private TokenService tokenService;

    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public List<User> getAllUsers(){
        System.out.println("Авторизован");
        return userService.getAllUsers();
    }

    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public User getUserById(@Argument Long id){
        return userService.findUserById(id);
    }

    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }

    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public Role getRoleById(@Argument Long id){
        return roleService.findRoleById(id);
    }

    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public Token getTokenById(@Argument Long id){
        return tokenService.findTokenById(id);
    }

    @MutationMapping
    public UserCreateResDTO createUser(@Argument UserCreateReqDTO input, HttpServletRequest request){
        HeaderDTO headerDto = HeaderDTO.builder()
                .IP(request.getRemoteAddr())
                .userAgent(request.getHeader("User-Agent")).build();
        return userService.createUser(input, headerDto);
    }

    @MutationMapping
    public TokensDTO login(@Argument LoginDTO input){
        return userService.login(input);
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public User updateUser(@Argument UserUpdateDTO input){
        long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        return userService.updateUser(input, userId);
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public String deleteUser(@Argument Long id){
        userService.deleteUser(id);
        return "The user has been successfully deleted";
    }

    @MutationMapping
    public TokensDTO updateTokens(@Argument String refresh, HttpServletRequest request){
        HeaderDTO headerDto = HeaderDTO.builder()
                .IP(request.getRemoteAddr())
                .userAgent(request.getHeader("User-Agent")).build();
        return userService.updateRefreshTokenOfUser(TokenCreateDTO.builder()
                .refresh(refresh)
                .userAgent(headerDto.getUserAgent())
                .IP(headerDto.getIP())
                .build());
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public String passwordChange(@Argument PasswordChangeDTO input){
        long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        userService.passwordChangeOfUser(input, userId);
        return "The password has been successfully changed";
    }

    @MutationMapping
    public String assignRole(@Argument UserRoles role){
        long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        userService.addRole(role, userId);
        return "Role added successfully";
    }

    @MutationMapping
    public String takeRole(@Argument UserRoles role){
        long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        userService.removeRole(role, userId);
        return "The role was successfully removed";
    }
}
