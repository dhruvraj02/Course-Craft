package com.project.CourseCraft.dao;

import com.project.CourseCraft.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseCategoryRepository extends JpaRepository<Courses, Integer> {
}
