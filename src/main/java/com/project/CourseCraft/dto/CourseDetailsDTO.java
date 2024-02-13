package com.project.CourseCraft.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseDetailsDTO {

    private int assignmentID;

    private String assignmentName;

    private String assignmentLink;

    private int materialID;

    private String materialLink;

    private int videoID;

    private String videoName;

    private String videoLink;

}
