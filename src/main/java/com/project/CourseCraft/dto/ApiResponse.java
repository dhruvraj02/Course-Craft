package com.project.CourseCraft.dto;

import lombok.*;

/**
 * Data Transfer Object (DTO) representing an API response.
 * <p>
 * This DTO is used for encapsulating the response information returned to users for their actions.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResponse {

    private String message;

    private boolean success;

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "message='" + message + '\'' +
                ", success=" + success +
                '}';
    }
}
