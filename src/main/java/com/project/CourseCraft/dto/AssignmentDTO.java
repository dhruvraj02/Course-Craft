package com.project.CourseCraft.dto;

import lombok.*;

/**
 * Data Transfer Object (DTO) representing an assignment of a course.
 *<p>
 * This DTO is used for transferring assignment data between different layers of the application.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AssignmentDTO {

    private int assignmentID;

    private String assignmentName;

    private String assignmentLink;

}
