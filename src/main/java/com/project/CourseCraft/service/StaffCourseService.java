package com.project.CourseCraft.service;

import com.project.CourseCraft.dto.CourseAllDTO;
import com.project.CourseCraft.dto.StaffCourseDTO;
import com.project.CourseCraft.entity.Staff;

import java.util.List;

public interface StaffCourseService {

    StaffCourseDTO createCourse(StaffCourseDTO courseDTO, Integer uid, Integer courseID);

    StaffCourseDTO updateCourse(StaffCourseDTO courseDTO, Integer courseUploadID);

    void deleteCourse(Integer courseUploadID);

    List<CourseAllDTO> getAllStaffCourse();

    StaffCourseDTO getStaffByID(Integer courseUploadID);

    List<StaffCourseDTO> getCourseByCategory(Integer courseID);

    List<StaffCourseDTO> getCourseByUser(Integer uid);

    List<StaffCourseDTO> searchCourse(String keyword);

    List<CourseAllDTO> getNotEnrolledCourse(Integer userID);

}
