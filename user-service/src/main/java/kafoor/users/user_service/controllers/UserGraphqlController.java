package kafoor.users.user_service.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import kafoor.users.user_service.models.User;
import kafoor.users.user_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Tag(name = "User-GraphQL", description = "GraphQL operations for user management")
@Controller
public class UserGraphqlController {
    @Autowired
    private UserService userService;

    @QueryMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @QueryMapping
    public User getUserById(@Argument Long id){
        return userService.findUserById(id);
    }
}
