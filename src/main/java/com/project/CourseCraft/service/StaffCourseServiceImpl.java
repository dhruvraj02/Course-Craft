package com.project.CourseCraft.service;

import com.project.CourseCraft.dao.CourseCategoryRepository;
import com.project.CourseCraft.dao.StaffCourseRepository;
import com.project.CourseCraft.dao.UserRepository;
import com.project.CourseCraft.dto.*;
import com.project.CourseCraft.entity.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import com.project.CourseCraft.exception.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StaffCourseServiceImpl implements StaffCourseService {

    @Autowired
    private StaffCourseRepository staffCourseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseCategoryRepository courseCategoryRepository;

    @Autowired
    StudentEnrollService enrollService;

    Calendar calendar = Calendar.getInstance();

    /**
     * Creates a new course upload by a staff member.
     *
     * @param courseDTO   the staff course DTO containing the upload details
     * @param uid         the ID of the user associated with the upload
     * @param courseID    the ID of the course category associated with the upload
     * @return the created staff course DTO
     * @throws ResourceNotFoundException if the user or course category is not found
     */
    @Override
    public StaffCourseDTO createCourse(StaffCourseDTO courseDTO, Integer uid, Integer courseID) {
        // Retrieve the user with the given user ID
        User user = this.userRepository.findById(uid).orElseThrow(() ->
                new ResourceNotFoundException("User", "User ID", uid));

        // Retrieve the course category with the given course ID
        Courses course = this.courseCategoryRepository.findById(courseID).orElseThrow(() ->
                new ResourceNotFoundException("Category", "Course Category ID", courseID));

        // Create a new staff course upload
        Staff staff = new Staff();
        staff.setCourseUploadName(courseDTO.getCourseUploadName());

        // Set the upload date to the current date
        staff.setUploadDate(new Date());

        // Set the disable date to 6 months after the upload date
        calendar.setTime(staff.getUploadDate());
        calendar.add(Calendar.MONTH, 6);
        staff.setDisableDate(calendar.getTime());

        // Set the associated user and course category
        staff.setUser(user);
        staff.setCourses(course);

        // Save the new staff course upload
        Staff newCourseUpload = this.staffCourseRepository.save(staff);

        // Map the saved staff course upload to its DTO and return it
        return mapper(newCourseUpload);
    }

    /**
     * Updates an existing staff course upload.
     *
     * @param courseDTO       the updated staff course DTO
     * @param courseUploadID  the ID of the staff course upload to update
     * @return the updated staff course DTO
     */
    @Override
    public StaffCourseDTO updateCourse(StaffCourseDTO courseDTO, Integer courseUploadID) {

        Staff staff = this.staffCourseRepository.findById(courseUploadID).orElseThrow(
                () -> new ResourceNotFoundException("Course", "id", courseUploadID)
        );

        staff.setCourseUploadName(courseDTO.getCourseUploadName());

        Staff updatedCourse = this.staffCourseRepository.save(staff);

        return mapper(updatedCourse);
    }

    /**
     * Deletes a staff course upload with the specified ID.
     *
     * @param courseUploadID  the ID of the staff course upload to delete
     */
    @Override
    public void deleteCourse(Integer courseUploadID) {

        Staff staff = this.staffCourseRepository.findById(courseUploadID).orElseThrow(
                () -> new ResourceNotFoundException("Course", "id", courseUploadID)
        );

        this.staffCourseRepository.delete(staff);

    }

    /**
     * Retrieves all staff course uploads.
     *
     * @return a list of all staff course DTOs
     */
    @Override
    public List<CourseAllDTO> getAllStaffCourse() {
        // Retrieve all courses from the repository
        List<Staff> courses = this.staffCourseRepository.findAll();

        // Create a list to hold the course category DTOs
        List<CourseAllDTO> courseDTOList = new ArrayList<>();

        // Check if there are no courses found
        if (courses == null) {
            throw new ResourceNotFoundException("Course Category", "any", 0);
        }

        // Maps each course to CourseAllDTO
        for (Staff course : courses) {
            CourseAllDTO courseDTO = new CourseAllDTO();
            courseDTO = this.modelMapper.map(course, CourseAllDTO.class);

            courseDTO.setUploadDate(String.valueOf(course.getUploadDate()).substring(0,10));
            courseDTO.setDisableDate(String.valueOf(course.getDisableDate()).substring(0,10));

            courseDTO.setCourseCategory(course.getCourses().getCourseName());

            courseDTO.setUserName(course.getUser().getFullName());

            // Add the CourseAllDTO to the list
            courseDTOList.add(courseDTO);
        }

        return courseDTOList;
    }

    /**
     * Retrieves a staff course upload by ID.
     *
     * @param courseUploadID  the ID of the staff course upload
     * @return the staff course DTO
     * @throws ResourceNotFoundException if the staff course upload is not found
     */
    @Override
    public StaffCourseDTO getStaffByID(Integer courseUploadID) {

        Staff staff = this.staffCourseRepository.findById(courseUploadID).orElseThrow(
                () -> new ResourceNotFoundException("Course", "id", courseUploadID)
        );

        return mapper(staff);
    }

    /**
     * Retrieves all staff course uploads for a specific course category.
     *
     * @param idcourse  the ID of the course category
     * @return a list of staff course DTOs
     * @throws ResourceNotFoundException if the course category is not found
     */
    @Override
    public List<StaffCourseDTO> getCourseByCategory(Integer idcourse) {

        Courses courses = this.courseCategoryRepository.findById(idcourse).orElseThrow(
                () -> new ResourceNotFoundException("Course", "id", idcourse)
        );

        List<Staff> staffList = this.staffCourseRepository.findByCourses(courses);

        return mapper(staffList);

    }

    /**
     * Retrieves all staff course uploads for a specific user.
     *
     * @param uid  the ID of the user
     * @return a list of staff course DTOs
     * @throws ResourceNotFoundException if the user is not found
     */
    @Override
    public List<StaffCourseDTO> getCourseByUser(Integer uid) {

        User user = this.userRepository.findById(uid).orElseThrow(
                () -> new ResourceNotFoundException("User ", " ID", uid)
        );

        List<Staff> staffList = this.staffCourseRepository.findByUser(user);

        return mapper(staffList);
    }

    /**
     * Searches for staff course uploads containing a specific keyword in the course upload name.
     *
     * @param keyword  the keyword to search for
     * @return a list of matching staff course DTOs
     * @throws ResourceNotFoundException if no matching staff course uploads are found
     */
    @Override
    public List<StaffCourseDTO> searchCourse(String keyword) {
        List<Staff> staffCourse = this.staffCourseRepository.findByCourseUploadNameContaining(keyword);

        if (staffCourse == null) throw new ResourceNotFoundException("Course", "Keyword", 404);

        return mapper(staffCourse);
    }

    /**
     * Retrieves a list of courses in which a user is not enrolled.
     *
     * @param userID the ID of the user
     * @return a list of CourseAllDTO objects representing the courses
     */
    @Override
    public List<CourseAllDTO> getNotEnrolledCourse(Integer userID) {

        // Retrieve all courses from the repository
        List<Staff> courses = this.staffCourseRepository.findAll();

        // Create a list to hold the course category DTOs
        List<CourseAllDTO> courseDTOList = new ArrayList<>();

        // Maps each course to CourseAllDTO
        for (Staff course : courses) {
            CourseAllDTO courseDTO = new CourseAllDTO();
            courseDTO = this.modelMapper.map(course, CourseAllDTO.class);

            // Extract and format upload and disable dates
            courseDTO.setUploadDate(String.valueOf(course.getUploadDate()).substring(0, 10));
            courseDTO.setDisableDate(String.valueOf(course.getDisableDate()).substring(0, 10));

            courseDTO.setCourseCategory(course.getCourses().getCourseName());

            courseDTO.setUserName(course.getUser().getFullName());

            // Add the CourseAllDTO to the list
            courseDTOList.add(courseDTO);
        }

        // Retrieve enrolled courses for the user
        List<StudentCourseEnrollDTO> enrollDTOList = this.enrollService.getEnrollByUser(userID);

        // Check if the user is enrolled in these courses
        for (int i = 0; i < courseDTOList.size(); i++) {
            for (StudentCourseEnrollDTO studentCourseEnrollDTO : enrollDTOList) {
                // If a user is enrolled in some course, remove it from the list
                if (Objects.equals(courseDTOList.get(i).getCourseUploadID(), studentCourseEnrollDTO.getCourseUploadID())) {
                    courseDTOList.remove(i);
                }
            }
        }

        // Return the list of courses in which the user is not enrolled
        return courseDTOList;
    }


    /**
     * Maps a Staff entity to its corresponding DTO.
     *
     * @param course  the Staff entity
     * @return the corresponding StaffCourseDTO
     */
    private StaffCourseDTO mapper(Staff course) {
        StaffCourseDTO courseDTO = new StaffCourseDTO();

        // Set basic properties
        courseDTO.setCourseUploadID(course.getCourseUploadID());
        courseDTO.setCourseUploadName(course.getCourseUploadName());
        courseDTO.setUploadDate(course.getUploadDate());
        courseDTO.setDisableDate(course.getDisableDate());
        courseDTO.setUserName(course.getUser().getFullName());

        // Map CourseCategory to CourseCategoryDTO
        courseDTO.setCourseCategory(course.getCourses().getCourseName());

        // Map Material list to MaterialDTO list
        {
            TypeMap<Material, MaterialDTO> materialTypeMap = modelMapper.getTypeMap(Material.class, MaterialDTO.class);
            if(materialTypeMap == null) materialTypeMap = modelMapper.createTypeMap(Material.class, MaterialDTO.class);
            courseDTO.setMaterial(course.getMaterial().stream()
                    .map(materialTypeMap::map)
                    .collect(Collectors.toList()));
        }

        // Map Assignment list to AssignmentDTO list
        {
            TypeMap<Assignment, AssignmentDTO> assignmentTypeMap = modelMapper.getTypeMap(Assignment.class, AssignmentDTO.class);
            if (assignmentTypeMap == null) assignmentTypeMap = modelMapper.createTypeMap(Assignment.class, AssignmentDTO.class);
            courseDTO.setAssignment(course.getAssignment().stream()
                    .map(assignmentTypeMap::map)
                    .collect(Collectors.toList()));
        }

        // Map Video list to VideoDTO list
        {
            TypeMap<Video, VideoDTO> videoTypeMap = modelMapper.getTypeMap(Video.class, VideoDTO.class);
            if(videoTypeMap == null) videoTypeMap = modelMapper.createTypeMap(Video.class, VideoDTO.class);
            courseDTO.setVideo(course.getVideo().stream()
                    .map(videoTypeMap::map)
                    .collect(Collectors.toList()));
        }

        return courseDTO;
    }


    /**
     * Maps a StaffCourseDTO to its corresponding Staff entity.
     *
     * @param courseDTO  the StaffCourseDTO
     * @return the corresponding Staff entity
     */
    private Staff mapper(StaffCourseDTO courseDTO) {
        Staff staff = new Staff();

        // Set basic properties
        staff.setCourseUploadName(courseDTO.getCourseUploadName());
        staff.setUploadDate(courseDTO.getUploadDate());
        staff.setDisableDate(courseDTO.getDisableDate());

        // Map CourseCategoryDTO to CourseCategory
        staff.setCourses(modelMapper.map(courseDTO.getCourseCategory(), Courses.class));

        // Map MaterialDTO list to a Material list
        {
            TypeMap<MaterialDTO, Material> materialTypeMap = modelMapper.getTypeMap(MaterialDTO.class, Material.class);
            if (materialTypeMap == null) materialTypeMap = modelMapper.createTypeMap(MaterialDTO.class, Material.class);
            staff.setMaterial(courseDTO.getMaterial().stream()
                    .map(materialTypeMap::map)
                    .collect(Collectors.toList()));
        }

        // Map AssignmentDTO list to an Assignment list
        {
            TypeMap<AssignmentDTO, Assignment> assignmentTypeMap = modelMapper.getTypeMap(AssignmentDTO.class, Assignment.class);
            if (assignmentTypeMap == null) assignmentTypeMap = modelMapper.createTypeMap(AssignmentDTO.class, Assignment.class);
            staff.setAssignment(courseDTO.getAssignment().stream()
                    .map(assignmentTypeMap::map)
                    .collect(Collectors.toList()));
        }

        // Map VideoDTO list to a Video list
        {
            TypeMap<VideoDTO, Video> videoTypeMap = modelMapper.getTypeMap(VideoDTO.class, Video.class);
            if(videoTypeMap == null) videoTypeMap = modelMapper.createTypeMap(VideoDTO.class, Video.class);
            staff.setVideo(courseDTO.getVideo().stream()
                    .map(videoTypeMap::map)
                    .collect(Collectors.toList()));
        }

        return staff;
    }

    /**
     * Maps a list of Staff entities to a list of StaffCourseDTOs.
     *
     * @param courseList the list of Staff entities to be mapped
     * @return the list of StaffCourseDTOs
     */
    private List<StaffCourseDTO> mapper(List<Staff> courseList) {
        List<StaffCourseDTO> courseDTOList = new ArrayList<>();

        // Iterate over each Staff entity in the courseList
        for (Staff course : courseList) {
            // Map the Staff entity to its corresponding StaffCourseDTO using the mapper method
            StaffCourseDTO courseDTO = mapper(course);
            // Add the StaffCourseDTO to the list
            courseDTOList.add(courseDTO);
        }

        return courseDTOList;
    }

}
