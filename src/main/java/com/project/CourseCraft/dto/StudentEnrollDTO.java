package com.project.CourseCraft.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentEnrollDTO {

    private String userName;

    private Date enrollDate;

    private Date expiryDate;

}
