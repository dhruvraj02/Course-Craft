package com.project.CourseCraft.service;

import com.project.CourseCraft.dto.CourseAllDTO;
import com.project.CourseCraft.dto.StudentCourseEnrollDTO;
import com.project.CourseCraft.dto.StudentEnrollDTO;

import java.util.List;

public interface StudentEnrollService {

    List<StudentCourseEnrollDTO> getEnrollByUser(Integer uid);

    List<StudentEnrollDTO> getEnrollByStaffCourse(Integer courseID);

    StudentEnrollDTO enrollStudent(Integer uid, Integer courseID);

    void removeEnrolledStudent(Integer uid, Integer courseID);

    StudentEnrollDTO changeExpiryDateByMonth(Integer uid, Integer courseID, Integer months);

}