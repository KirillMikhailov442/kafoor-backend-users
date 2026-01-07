package dev.kafoor.users.controller.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ping")
@RestController
@RequestMapping("api/v1/ping")
public class PingController {
    @GetMapping
    public ResponseEntity<String> pong(){
        return ResponseEntity.ok("pong");
    }
}
