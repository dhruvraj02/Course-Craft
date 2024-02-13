package com.project.CourseCraft.service;

import com.project.CourseCraft.dto.JwtAuthRequest;
import com.project.CourseCraft.dto.JwtAuthResponse;
import com.project.CourseCraft.dto.UserDTO;
import com.project.CourseCraft.exception.ApiException;
import com.project.CourseCraft.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Authenticates the user using the provided JWT authentication request and generates a JWT token
     * if the credentials are valid. The generated token can be used for subsequent API requests to
     * authenticate the user.
     * <p>
     * POST /auth/login
     *
     * @param request the JWT authentication request containing the user credentials
     * @return the JWT authentication response containing the token and user details
     */
    @Override
    public JwtAuthResponse login(JwtAuthRequest request) {
        // Authenticate the user using the provided credentials
        this.authenticate(request.getUserID(), request.getPassword());

        // Load the user details to generate the JWT token
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUserID());

        // Generate a JWT token using the user details
        String token = this.jwtTokenHelper.generateToken(userDetails);

        // Prepare the JWT authentication response
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);

        // Retrieve additional user details from the user service
        UserDTO userDTO = userService.findByUserID(request.getUserID());
        response.setId(userDTO.getId());
        response.setUserRole(userDTO.getRole());

        return response;
    }

    /**
     * Authenticates the user using the provided credentials.
     *
     * @param userID   the user ID
     * @param password the user password
     * @throws ApiException if the authentication fails
     */
    private void authenticate(String userID, String password) throws ApiException {
        // Create an authentication token with the user ID and password
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userID, password);
        try {
            // Perform authentication
            this.authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            System.out.println("Invalid Credentials !!");
            throw new ApiException("Invalid username or password !!");
        }
    }
}
