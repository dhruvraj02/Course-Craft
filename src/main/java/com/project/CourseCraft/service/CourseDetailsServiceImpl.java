package com.project.CourseCraft.service;

import com.project.CourseCraft.dao.AssignmentRepository;
import com.project.CourseCraft.dao.MaterialRepository;
import com.project.CourseCraft.dao.StaffCourseRepository;
import com.project.CourseCraft.dao.VideoRepository;
import com.project.CourseCraft.dto.*;
import com.project.CourseCraft.entity.Assignment;
import com.project.CourseCraft.entity.Material;
import com.project.CourseCraft.entity.Staff;
import com.project.CourseCraft.entity.Video;
import com.project.CourseCraft.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseDetailsServiceImpl implements CourseDetailsService{

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    StaffCourseRepository staffCourseRepository;

    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    VideoRepository videoRepository;

    /**
     * Retrieves a list of assignments associated with a staff member's course upload.
     *
     * @param courseUploadID The ID of the course upload.
     * @return A list of AssignmentDTO representing the assignments.
     */
    @Override
    public List<AssignmentDTO> getAssignment(Integer courseUploadID) {
        // Find the staff member by the course upload ID, or throw an exception if not found
        Staff staff = this.staffCourseRepository.findById(courseUploadID).orElseThrow(
                () -> new ResourceNotFoundException("Course", "id", courseUploadID)
        );

        // Retrieve the list of assignments associated with the staff member's course upload
        List<Assignment> assignmentList = this.assignmentRepository.findByStaffCourse(staff);

        // Configure the mapping from Assignment to AssignmentDTO using modelMapper
        TypeMap<Assignment, AssignmentDTO> assignmentTypeMap = modelMapper.getTypeMap(Assignment.class, AssignmentDTO.class);
        if (assignmentTypeMap == null) {
            assignmentTypeMap = modelMapper.createTypeMap(Assignment.class, AssignmentDTO.class);
        }

        // Map each Assignment object to AssignmentDTO and collect them into a list
        return assignmentList.stream()
                .map(assignmentTypeMap::map)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of materials associated with a staff member's course upload.
     *
     * @param courseUploadID The ID of the course upload.
     * @return A list of MaterialDTO representing the materials.
     */
    @Override
    public List<MaterialDTO> getMaterial(Integer courseUploadID) {
        // Find the staff member by the course upload ID, or throw an exception if not found
        Staff staff = this.staffCourseRepository.findById(courseUploadID).orElseThrow(
                () -> new ResourceNotFoundException("Course", "id", courseUploadID)
        );

        // Retrieve the list of materials associated with the staff member's course upload
        List<Material> materialList = this.materialRepository.findByStaffCourse(staff);

        // Configure the mapping from Material to MaterialDTO using modelMapper
        TypeMap<Material, MaterialDTO> materialTypeMap = modelMapper.getTypeMap(Material.class, MaterialDTO.class);
        if (materialTypeMap == null) {
            materialTypeMap = modelMapper.createTypeMap(Material.class, MaterialDTO.class);
        }

        // Map each Material object to MaterialDTO and collect them into a list
        return materialList.stream()
                .map(materialTypeMap::map)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of videos associated with a staff member's course upload.
     *
     * @param courseUploadID The ID of the course upload.
     * @return A list of VideoDTO representing the videos.
     */
    @Override
    public List<VideoDTO> getVideo(Integer courseUploadID) {
        // Find the staff member by the course upload ID, or throw an exception if not found
        Staff staff = this.staffCourseRepository.findById(courseUploadID).orElseThrow(
                () -> new ResourceNotFoundException("Course", "id", courseUploadID)
        );

        // Retrieve the list of videos associated with the staff member's course upload
        List<Video> videoList = this.videoRepository.findByStaffCourse(staff);

        // Configure the mapping from Video to VideoDTO using modelMapper
        TypeMap<Video, VideoDTO> videoTypeMap = modelMapper.getTypeMap(Video.class, VideoDTO.class);
        if (videoTypeMap == null) {
            videoTypeMap = modelMapper.createTypeMap(Video.class, VideoDTO.class);
        }

        // Map each Video object to VideoDTO and collect them into a list
        return videoList.stream()
                .map(videoTypeMap::map)
                .collect(Collectors.toList());
    }

    /**
     * Adds course details to the system.
     *
     * @param courseDetailsDTO The DTO containing the course details.
     * @param courseUploadID   The ID of the course upload.
     * @throws ResourceNotFoundException if the course upload ID is not found.
     */
    @Override
    public void addCourseDetails(CourseDetailsDTO courseDetailsDTO, Integer courseUploadID) {
        // Find the staff member by the course upload ID, or throw an exception if not found
        Staff staff = this.staffCourseRepository.findById(courseUploadID).orElseThrow(
                () -> new ResourceNotFoundException("Course", "id", courseUploadID)
        );

        // Create new instances of assignment, material, and video
        Assignment assignment = new Assignment();
        Material material = new Material();
        Video video = new Video();

        // Map the course details DTO to the corresponding entities
        mapper(courseDetailsDTO, staff, assignment, material, video);

        // Save the assignment, material, and video entities
        this.assignmentRepository.save(assignment);
        this.materialRepository.save(material);
        this.videoRepository.save(video);
    }

    /**
     * Adds an assignment to a staff member's course upload.
     *
     * @param assignmentDTO   The AssignmentDTO containing the assignment details.
     * @param courseUploadID  The ID of the course upload.
     */
    public void addAssignment(AssignmentDTO assignmentDTO, Integer courseUploadID) {
        // Find the staff member by the course upload ID, or throw an exception if not found
        Staff staff = this.staffCourseRepository.findById(courseUploadID).orElseThrow(
                () -> new ResourceNotFoundException("Course", "id", courseUploadID)
        );

        Assignment assignment = new Assignment();
        assignment = this.modelMapper.map(assignmentDTO, Assignment.class);
        assignment.setStaffCourse(staff);

        this.assignmentRepository.save(assignment);
    }

    /**
     * Adds a material to a staff member's course upload.
     *
     * @param materialDTO      The MaterialDTO containing the material details.
     * @param courseUploadID   The ID of the course upload.
     */
    public void addMaterial(MaterialDTO materialDTO, Integer courseUploadID) {
        // Find the staff member by the course upload ID, or throw an exception if not found
        Staff staff = this.staffCourseRepository.findById(courseUploadID).orElseThrow(
                () -> new ResourceNotFoundException("Course", "id", courseUploadID)
        );

        Material material = new Material();
        material = this.modelMapper.map(materialDTO, Material.class);
        material.setStaffCourse(staff);

        this.materialRepository.save(material);
    }

    /**
     * Adds a video to a staff member's course upload.
     *
     * @param videoDTO         The VideoDTO containing the video details.
     * @param courseUploadID   The ID of the course upload.
     */
    public void addVideo(VideoDTO videoDTO, Integer courseUploadID) {
        // Find the staff member by the course upload ID, or throw an exception if not found
        Staff staff = this.staffCourseRepository.findById(courseUploadID).orElseThrow(
                () -> new ResourceNotFoundException("Course", "id", courseUploadID)
        );

        Video video = new Video();
        video = this.modelMapper.map(videoDTO, Video.class);
        video.setStaffCourse(staff);

        this.videoRepository.save(video);
    }

    /**
     * Updates an assignment associated with a staff member's course upload.
     *
     * @param assignmentDTO   The AssignmentDTO containing the updated assignment information.
     */
    @Override
    public void updateAssignment(AssignmentDTO assignmentDTO, Integer id) {

        // Find the assignment by the ID, or throw an exception if not found
        Assignment assignment = this.assignmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Assignment", "ID", id)
        );

        assignment.setAssignmentName(assignmentDTO.getAssignmentName());
        assignment.setAssignmentLink(assignmentDTO.getAssignmentLink());

        // Save the updated assignment
        this.assignmentRepository.save(assignment);
    }

    /**
     * Updates a material associated with a staff member's course upload.
     *
     * @param materialDTO     The MaterialDTO containing the updated material information.
     * @param id              The ID of the material.
     */
    @Override
    public void updateMaterial(MaterialDTO materialDTO, Integer id) {

        // Find the material by the ID, or throw an exception if not found
        Material material = this.materialRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Material", "ID", id)
        );

        material.setMaterialLink(materialDTO.getMaterialLink());

        // Save the updated material
        this.materialRepository.save(material);
    }

    /**
     * Updates a video associated with a staff member's course upload.
     *
     * @param videoDTO        The VideoDTO containing the updated video information.
     * @param id              The ID of the video.
     */
    @Override
    public void updateVideo(VideoDTO videoDTO, Integer id) {

        // Find the video by the ID, or throw an exception if not found
        Video video = this.videoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Video", "ID", id)
        );

        video.setVideoName(video.getVideoName());
        video.setVideoLink(video.getVideoLink());

        // Save the updated video
        this.videoRepository.save(video);
    }


    /**
     * Deletes an assignment.
     *
     * @param assignmentID The ID of the assignment to be deleted.
     */
    @Override
    public void deleteAssignment(Integer assignmentID) {
        // Find the assignment by the ID, or throw an exception if not found
        Assignment assignment = this.assignmentRepository.findById(assignmentID).orElseThrow(
                () -> new ResourceNotFoundException("Assignment", "ID", assignmentID)
        );

        // Delete the assignment
        this.assignmentRepository.delete(assignment);
    }

    /**
     * Deletes a material.
     *
     * @param materialID The ID of the material to be deleted.
     */
    @Override
    public void deleteMaterial(Integer materialID) {
        // Find the material by the ID, or throw an exception if not found
        Material material = this.materialRepository.findById(materialID).orElseThrow(
                () -> new ResourceNotFoundException("Material", "ID", materialID)
        );

        // Delete the material
        this.materialRepository.delete(material);
    }

    /**
     * Deletes a video.
     *
     * @param videoID The ID of the video to be deleted.
     */
    @Override
    public void deleteVideo(Integer videoID) {
        // Find the video by the ID, or throw an exception if not found
        Video video = this.videoRepository.findById(videoID).orElseThrow(
                () -> new ResourceNotFoundException("Video", "ID", videoID)
        );

        // Delete the video
        this.videoRepository.delete(video);
    }



    /**
     * Maps the course details DTO to assignment, material, and video entities.
     *
     * @param courseDetailsDTO The DTO containing the course details.
     * @param staff            The staff member associated with the course.
     * @param assignment       The assignment entity to be mapped.
     * @param material         The material entity to be mapped.
     * @param video            The video entity to be mapped.
     */
    private void mapper(CourseDetailsDTO courseDetailsDTO,
                        Staff staff,
                        Assignment assignment,
                        Material material,
                        Video video) {

        // Create new instances of assignment DTO, material DTO, and video DTO
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        MaterialDTO materialDTO = new MaterialDTO();
        VideoDTO videoDTO = new VideoDTO();

        // Map the course details DTO to assignment DTO, material DTO, and video DTO
        assignmentDTO = this.modelMapper.map(courseDetailsDTO, AssignmentDTO.class);
        materialDTO = this.modelMapper.map(courseDetailsDTO, MaterialDTO.class);
        videoDTO = this.modelMapper.map(courseDetailsDTO, VideoDTO.class);

        // Map assignment DTO to assignment entity and set the associated staff member
        assignment = this.modelMapper.map(assignmentDTO, Assignment.class);
        assignment.setStaffCourse(staff);

        // Map material DTO to material entity and set the associated staff member
        material = this.modelMapper.map(materialDTO, Material.class);
        material.setStaffCourse(staff);

        // Map video DTO to video entity and set the associated staff member
        video = this.modelMapper.map(videoDTO, Video.class);
        video.setStaffCourse(staff);
    }

}
