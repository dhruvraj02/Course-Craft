package com.project.CourseCraft.controller;

import com.project.CourseCraft.dto.JwtAuthRequest;
import com.project.CourseCraft.dto.JwtAuthResponse;
import com.project.CourseCraft.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    /**
     * Authenticates the user and generates a JWT token.
     *
     * @param request the JWT authentication request containing user credentials
     * @return the JWT authentication response containing the token and user details
     */
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(
            @RequestBody JwtAuthRequest request
    ) {

        // delegating the login process
        JwtAuthResponse response = this.authService.login(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
