package com.project.CourseCraft.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentCourseEnrollDTO {

    private Integer courseUploadID;

    private String enrollDate;

    private String expiryDate;

    private String courseUploadName;

    private String userName;

    private String categoryName;

}
