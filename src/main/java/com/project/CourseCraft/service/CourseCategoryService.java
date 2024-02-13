package com.project.CourseCraft.service;

import com.project.CourseCraft.dto.CourseCategoryDTO;
import com.project.CourseCraft.entity.Courses;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface CourseCategoryService {

    List<CourseCategoryDTO> findAll();

    CourseCategoryDTO findById(Integer courseID);

    CourseCategoryDTO addCourseCategory(CourseCategoryDTO categoryDTO);

    CourseCategoryDTO updateCourseCategory(CourseCategoryDTO categoryDTO, Integer courseID);

    void deleteCourseCategory(Integer courseID);

}
