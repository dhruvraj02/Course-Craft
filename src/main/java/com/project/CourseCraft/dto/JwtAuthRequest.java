package com.project.CourseCraft.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) representing the login info.
 *<p>
 * This DTO is used for transferring JWT request data between different layers of the application.
 */
@Data
public class JwtAuthRequest {

    private String userID;

    private String password;

}
