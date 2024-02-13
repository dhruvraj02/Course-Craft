package com.project.CourseCraft.dao;

import com.project.CourseCraft.entity.Assignment;
import com.project.CourseCraft.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {

    List<Assignment> findByStaffCourse(Staff staff);

}