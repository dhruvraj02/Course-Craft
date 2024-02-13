package com.project.CourseCraft.dao;

import com.project.CourseCraft.entity.Assignment;
import com.project.CourseCraft.entity.Staff;
import com.project.CourseCraft.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {

    List<Video> findByStaffCourse(Staff staff);

}
