package com.project.CourseCraft.dto;

import com.project.CourseCraft.entity.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  Data Transfer Object (DTO) representing course data.
 * <p>
 *  This DTO is used for transferring course data between different layers of the application.
 * <p>
 *  And for displaying course information on the frontend.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StaffCourseDTO {

    @NotEmpty(message = "Course name cannot be empty !")

    private Integer courseUploadID;

    private String courseUploadName;

    private Date uploadDate;

    private Date disableDate;

    private String userName;

    private String courseCategory;

    private List<MaterialDTO> material = new ArrayList<>();

    private List<AssignmentDTO> assignment = new ArrayList<>();

    private List<VideoDTO> video = new ArrayList<>();

}
