package com.learning.ContactApp.controller;

import com.learning.ContactApp.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication API", description = "Login API to generate JWT token")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // ---------------- LOGIN API ----------------
    @PostMapping("/login")
    @Operation(
            summary = "Login and generate JWT token",
            description = """
                    Demo login API.

                    Test Credentials:
                    -----------------
                    username: admin
                    password: password

                    This will return a JWT token if credentials are correct.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "JWT token generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid credentials")
    })
    public Map<String, String> login(@RequestParam String username,
                                     @RequestParam String password) {

        if ("admin".equals(username) && "password".equals(password)) {
            String token = jwtUtil.generateToken(username);
            return Map.of("token", token);
        }

        throw new RuntimeException("Invalid credentials");
    }
}