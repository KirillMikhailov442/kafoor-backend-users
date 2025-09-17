package kafoor.users.user_service.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import kafoor.users.user_service.models.Role;
import kafoor.users.user_service.models.Token;
import kafoor.users.user_service.models.User;
import kafoor.users.user_service.services.RoleService;
import kafoor.users.user_service.services.TokenService;
import kafoor.users.user_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
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

    @QueryMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @QueryMapping
    public User getUserById(@Argument Long id){
        return userService.findUserById(id);
    }

    @QueryMapping
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }

    @QueryMapping
    public Role getRoleById(@Argument Long id){
        return roleService.findRoleById(id);
    }

    @QueryMapping
    public Token getTokenById(@Argument Long id){
        return tokenService.findTokenById(id);
    }
}
