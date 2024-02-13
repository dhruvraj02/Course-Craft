package com.project.CourseCraft.controller;

import com.project.CourseCraft.dto.ApiResponse;
import com.project.CourseCraft.dto.CourseAllDTO;
import com.project.CourseCraft.dto.StaffCourseDTO;
import com.project.CourseCraft.service.StaffCourseService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/v3")
public class CourseController {

    @Autowired
    StaffCourseService courseService;

    /**
     * Retrieves all courses.
     *
     * <p>
     * GET /v3/course
     *
     * @return ResponseEntity with a list of CourseAllDTO objects representing all courses and HTTP status 200 (OK)
     */
    @GetMapping("/course")
    public ResponseEntity<List<CourseAllDTO>> getAllCourses() {
        // Retrieve all courses using the courseService
        List<CourseAllDTO> courseDTOList = this.courseService.getAllStaffCourse();

        return new ResponseEntity<>(courseDTOList, HttpStatus.OK);
    }

    /**
     * Retrieves a course by its ID.
     *
     * <p>
     * GET /v3/course/{id}
     *
     * @param id The ID of the course to retrieve.
     * @return ResponseEntity with a StaffCourseDTO object representing the course and HTTP status 200 (OK)
     */
    @GetMapping("/course/{id}")
    public ResponseEntity<StaffCourseDTO> getCourseByID(@PathVariable("id") Integer id) {
        // Retrieve a course by its ID using the courseService
        StaffCourseDTO courseDTO = this.courseService.getStaffByID(id);

        return new ResponseEntity<>(courseDTO, HttpStatus.OK);
    }

    /**
     * Retrieves courses associated with a user.
     *
     * <p>
     * GET /v3/users/{uid}/course
     *
     * @param uid The ID of the user.
     * @return ResponseEntity with a list of StaffCourseDTO objects representing the user's courses and HTTP status 200 (OK)
     */
    @GetMapping("/users/{uid}/course")
    public ResponseEntity<List<StaffCourseDTO>> getCourseByUser(@PathVariable("uid") Integer uid) {
        // Retrieve courses associated with a user using the courseService
        List<StaffCourseDTO> courseDTOList = this.courseService.getCourseByUser(uid);

        return new ResponseEntity<>(courseDTOList, HttpStatus.OK);
    }

    /**
     * Retrieves courses associated with a category.
     *
     * <p>
     * GET /v3/category/{id}/course
     *
     * @param cid The ID of the category.
     * @return ResponseEntity with a list of StaffCourseDTO objects representing the category's courses and HTTP status 200 (OK)
     */
    @GetMapping("/category/{id}/course")
    public ResponseEntity<List<StaffCourseDTO>> getCourseByCategory(@PathVariable("id") Integer cid) {
        // Retrieve courses associated with a category using the courseService
        List<StaffCourseDTO> courseDTOList = this.courseService.getCourseByCategory(cid);

        return new ResponseEntity<>(courseDTOList, HttpStatus.OK);
    }

    /**
     * Searches for courses by course upload name.
     *
     * <p>
     * GET /v3/course/search/{keyword}
     *
     * @param keyword The keyword to search for.
     * @return ResponseEntity with a list of StaffCourseDTO objects matching the search criteria and HTTP status 200 (OK)
     */
    @GetMapping("/course/search/{keyword}")
    public ResponseEntity<List<StaffCourseDTO>> searchByCourseUploadName(@PathVariable String keyword) {
        // Search for courses by course upload name using the courseService
        List<StaffCourseDTO> courseDTOList = this.courseService.searchCourse(keyword);
        return new ResponseEntity<>(courseDTOList, HttpStatus.OK);
    }

    @GetMapping("/course-not-enrolled/users/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    public ResponseEntity<List<CourseAllDTO>> getCoursesByNotEnrolled(@PathVariable ("id") Integer uid){

        List<CourseAllDTO> courseAllDTOList = this.courseService.getNotEnrolledCourse(uid);

        return new ResponseEntity<>(courseAllDTOList, HttpStatus.OK);
    }

    /**
     * Adds a new course.
     *
     * <p>
     * POST /v3/users/{uID}/category/{categoryID}/course
     *
     * @param courseDTO  The course data to add.
     * @param uid        The ID of the user associated with the course.
     * @param courseID   The ID of the category associated with the course.
     * @return ResponseEntity with a StaffCourseDTO object representing the newly created course and HTTP status 200 (OK)
     */
    @PostMapping("/users/{uID}/category/{categoryID}/course")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<StaffCourseDTO> addCourse(@RequestBody StaffCourseDTO courseDTO,
                                                    @PathVariable("uID") Integer uid,
                                                    @PathVariable("categoryID") Integer courseID) {
        // Add a new course using the courseService
        StaffCourseDTO newCourse = this.courseService.createCourse(courseDTO, uid, courseID);

        return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
    }

    /**
     * Updates a course with the specified ID.
     *
     * <p>
     * PUT /v3/course/{id}
     *
     * @param courseDTO The updated course data.
     * @param cid       The ID of the course to update.
     * @return ResponseEntity with a StaffCourseDTO object representing the updated course and HTTP status 200 (OK)
     */
    @PutMapping("/course/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<StaffCourseDTO> updateCourse(@RequestBody StaffCourseDTO courseDTO,
                                                       @PathVariable("id") Integer cid) {
        // Update a course with the specified ID using the courseService
        StaffCourseDTO updatedCourse = this.courseService.updateCourse(courseDTO, cid);

        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }

    /**
     * Deletes a course with the specified ID.
     *
     * <p>
     * DELETE /v3/course/{id}
     *
     * @param cid The ID of the course to delete.
     * @return ResponseEntity with an ApiResponse object indicating the success of the deletion and HTTP status 200 (OK)
     */
    @DeleteMapping("/course/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    private ResponseEntity<ApiResponse> deleteCourse(@PathVariable("id") Integer cid) {
        // Delete a course with the specified ID using the courseService
        this.courseService.deleteCourse(cid);

        return new ResponseEntity<>(new ApiResponse("Course Removed", true), HttpStatus.OK);
    }

}
