package com.project.CourseCraft.dao;

import com.project.CourseCraft.entity.Courses;
import com.project.CourseCraft.entity.Staff;
import com.project.CourseCraft.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffCourseRepository extends JpaRepository<Staff, Integer> {

    List<Staff> findByUser(User user);
    List<Staff> findByCourses(Courses courses);
    List<Staff> findByCourseUploadNameContaining(String courseUploadName);
}
