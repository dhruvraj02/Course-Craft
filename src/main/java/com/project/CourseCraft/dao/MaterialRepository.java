package com.project.CourseCraft.dao;

import com.project.CourseCraft.entity.Assignment;
import com.project.CourseCraft.entity.Material;
import com.project.CourseCraft.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {

    List<Material> findByStaffCourse(Staff staff);

}

