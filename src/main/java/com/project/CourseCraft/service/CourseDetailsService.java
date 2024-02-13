package com.project.CourseCraft.service;

import com.project.CourseCraft.dto.*;

import java.util.List;

public interface CourseDetailsService {

    List<AssignmentDTO> getAssignment(Integer courseUploadID);

    List<MaterialDTO> getMaterial(Integer courseUploadID);

    List<VideoDTO> getVideo(Integer courseUploadID);

    void addCourseDetails(CourseDetailsDTO courseDetailsDTO, Integer courseUploadID);

    void addAssignment(AssignmentDTO assignmentDTO, Integer courseUploadID);

    void addMaterial(MaterialDTO materialDTO, Integer courseUploadID);

    void addVideo(VideoDTO videoDTO, Integer courseUploadID);

    void updateAssignment(AssignmentDTO assignmentDTO, Integer aid);

    void updateMaterial(MaterialDTO materialDTO, Integer mid);

    void updateVideo(VideoDTO videoDTO, Integer vid);

    void deleteAssignment(Integer assignmentID);

    void deleteMaterial(Integer materialID);

    void deleteVideo(Integer videoID);

}
