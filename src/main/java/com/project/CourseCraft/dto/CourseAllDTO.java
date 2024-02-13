package com.project.CourseCraft.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseAllDTO {

    private Integer courseUploadID;

    private String courseUploadName;

    private String uploadDate;

    private String disableDate;

    private String userName;

    private String courseCategory;

}
