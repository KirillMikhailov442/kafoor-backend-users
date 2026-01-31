package dev.kafoor.users.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Ping", description = "Simple health-check endpoint for smoke testing and service availability verification")
@RestController
@RequestMapping("api/v1/ping")
public class PingController {

        @Operation(summary = "Health check", description = "Returns 'pong' to confirm that the service is up and responding. Used for smoke tests, liveness probes, or basic connectivity checks.")
        @ApiResponse(responseCode = "200", description = "Service is operational", content = @Content(mediaType = "text/plain", schema = @Schema(type = "string", example = "pong")))
        @GetMapping
        public ResponseEntity<String> pong() {
                return ResponseEntity.ok("pong");
        }
}