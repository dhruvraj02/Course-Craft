package com.project.CourseCraft.dto;


import com.project.CourseCraft.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 *  Data Transfer Object (DTO) representing user data.
 * <p>
 *  This DTO is used for transferring user data between different layers of the application.
 * <p>
 *  And for displaying user information on the frontend.
 */
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class UserDTO {

    private int id;

    private String userID;

    private String password;

    @Size(min = 3, message = "Enter a valid name with atleast 3 letters !")
    private String fullName;

    @Email(message = "Email address is not valid !")
    private String email;

    @NotEmpty(message = "Role cannot be null !")
    private String role;

}
