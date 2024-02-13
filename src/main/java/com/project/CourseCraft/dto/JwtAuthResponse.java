package com.project.CourseCraft.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) representing JWT response.
 *<p>
 * displays the generated JWT at the frontend along with the user's role and id.
 */
@Data
public class JwtAuthResponse {

    private String token;

    private String userRole;

    private Integer id;

}
