package kafoor.users.user_service.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import kafoor.users.user_service.models.Role;
import kafoor.users.user_service.services.RoleService;
import kafoor.users.user_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Role", description = "Official role API")
@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    @Autowired
    RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getAll(){
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getOne(@PathVariable long id){
        return ResponseEntity.ok(roleService.findRoleById(id));
    }
}
