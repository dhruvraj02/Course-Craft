package com.project.CourseCraft.controller;

import com.project.CourseCraft.dto.ApiResponse;
import com.project.CourseCraft.dto.ChangePasswordDTO;
import com.project.CourseCraft.dto.UserDTO;
import com.project.CourseCraft.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/v1")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Retrieves a list of all users.
     * <p>
     * GET /v1/users
     *
     * @return ResponseEntity with a list of UserDTO objects and HTTP status 200 (OK)
     */
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * Retrieves a specific user by their ID.
     * <p>
     * GET /v1/users/{id}
     *
     * @param id The ID of the user to retrieve.
     * @return ResponseEntity with the UserDTO object of the specified user and HTTP status 200 (OK)
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.findByID(id));
    }

    /**
     * Creates a new user (other than an admin).
     * <p>
     * POST /v1/users
     *
     * @param userDTO The UserDTO object containing the user details.
     * @return ResponseEntity with the created UserDTO object and HTTP status 201 (Created)
     */
    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.registerNewUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Creates a new admin user.
     * (Note: This endpoint should only be used for creating a new admin.)
     * <p>
     * POST /v1/new-admin
     *
     * @param userDTO The UserDTO object containing the admin user details.
     * @return ResponseEntity with the created UserDTO object and HTTP status 201 (Created)
     */
    @PostMapping("/new-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> addAdmin(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.addAdmin(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Updates a specific user.
     * <p>
     * PUT /v1/users/{id}
     *
     * @param userDTO The UserDTO object containing the updated user details.
     * @param id The ID of the user to update.
     * @return ResponseEntity with the updated UserDTO object and HTTP status 200 (OK)
     */
    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> updatedUser(@Valid @RequestBody UserDTO userDTO, @PathVariable int id) {
        UserDTO updatedUser = userService.updateUser(userDTO, id);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Changes password of a specific user
     * <p>
     * PUT /v1/users-change-password/{id}
     *
     * @param changePasswordDTO The DTO containing new password
     * @param id The id of the user
     * @return Response Entity with HTTP Status code 200 (OK)
     */
    @PutMapping("/users-change-password/{id}")
    public ResponseEntity<ApiResponse> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO,
                                                      @PathVariable int id) {

        this.userService.updatePassword(changePasswordDTO, id);

        return new ResponseEntity<>(new ApiResponse("Password Changed", true), HttpStatus.OK);
    }

    /**
     * Deletes a user with the specified ID.
     * <p>
     * DELETE /v1/users/{id}
     *
     * @param id The ID of the user to delete.
     * @return ResponseEntity with an ApiResponse object indicating the success of the deletion and HTTP status 200 (OK)
     */
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(new ApiResponse("User Deleted", true), HttpStatus.OK);
    }
}
