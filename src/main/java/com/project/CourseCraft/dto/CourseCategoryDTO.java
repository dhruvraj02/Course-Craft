package com.project.CourseCraft.dto;

import lombok.*;

/**
 * Data Transfer Object (DTO) representing a Course Category.
 *<p>
 * This DTO is used for transferring course Category data between different layers of the application.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseCategoryDTO {

    String courseName;

    byte[] logo;

}
