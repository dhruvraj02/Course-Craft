package com.project.CourseCraft.dao;

import com.project.CourseCraft.entity.Staff;
import com.project.CourseCraft.entity.Student;
import com.project.CourseCraft.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentEnrollRepository extends JpaRepository<Student, Integer> {

    List<Student> findByUser(User user);

    List<Student> findByStaffCourse(Staff staff);

}
