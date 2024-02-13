package com.project.CourseCraft.dto;

import lombok.*;

/**
 * Data Transfer Object (DTO) representing a material.
 *<p>
 * This DTO is used for transferring material data between different layers of the application.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MaterialDTO {

    private int materialID;

    private String materialLink;

}
