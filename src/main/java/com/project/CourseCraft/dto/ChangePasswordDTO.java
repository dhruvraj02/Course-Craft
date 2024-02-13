package com.project.CourseCraft.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for changing password.
 */
@Getter
@Setter
public class ChangePasswordDTO {

    @Size(min = 4, max = 8, message = "Password length must be between 4 to 8 characters long!")
    String password;

}
