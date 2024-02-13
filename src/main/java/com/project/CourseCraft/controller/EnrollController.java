package com.project.CourseCraft.controller;

import com.project.CourseCraft.dto.ApiResponse;
import com.project.CourseCraft.dto.CourseAllDTO;
import com.project.CourseCraft.dto.StudentCourseEnrollDTO;
import com.project.CourseCraft.dto.StudentEnrollDTO;
import com.project.CourseCraft.service.StudentEnrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling student enrollment operations.
 */
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/v4")
public class EnrollController {

    @Autowired
    private StudentEnrollService enrollService;

    /**
     * Retrieves a list of student enrollments based on the provided user ID.
     * <p>
     * GET /v4/enroll/users/{uid}
     *
     * @param uid The ID of the user.
     * @return A ResponseEntity containing a list of StudentEnrollDTO representing the student enrollments. (HTTP status 200 OK)
     */
    @GetMapping("/enroll/users/{uid}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    public ResponseEntity<List<StudentCourseEnrollDTO>> getEnrollmentByUser(@PathVariable("uid") Integer uid) {
        List<StudentCourseEnrollDTO> courseAllDTOList = this.enrollService.getEnrollByUser(uid);
        return new ResponseEntity<>(courseAllDTOList, HttpStatus.OK);
    }

    /**
     * Retrieves a list of student enrollments based on the provided course ID.
     * <p>
     * GET /v4/enroll/course/{cid}
     *
     * @param cid The ID, of course.
     * @return A ResponseEntity containing a list of StudentEnrollDTO representing the student enrollments. (HTTP status 200 OK)
     */
    @GetMapping("/enroll/course/{cid}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<List<StudentEnrollDTO>> getEnrollmentByCourse(@PathVariable("cid") Integer cid) {
        List<StudentEnrollDTO> enrollDTOList = this.enrollService.getEnrollByStaffCourse(cid);
        return new ResponseEntity<>(enrollDTOList, HttpStatus.OK);
    }

    /**
     * Enrolls a student in a course based on the provided user ID and course ID.
     * <p>
     * POST /v4/users/{uid}/course/{cid}/enroll
     *
     * @param uid The ID of the user.
     * @param cid The ID, of course.
     * @return A ResponseEntity containing the StudentEnrollDTO representing the enrollment. (HTTP status 200 OK)
     */
    @PostMapping("/users/{uid}/course/{cid}/enroll")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    public ResponseEntity<StudentEnrollDTO> enrollStudent(@PathVariable("uid") Integer uid,
                                                          @PathVariable("cid") Integer cid) {
        StudentEnrollDTO enrollDTO = this.enrollService.enrollStudent(uid, cid);
        return new ResponseEntity<>(enrollDTO, HttpStatus.OK);
    }

    /**
     * Changes the expiry date of a student's enrollment based on the provided User ID, Course ID and months.
     * <p>
     * PUT /v4/enroll-changeExpiry/users/{userID}/course/{courseID}
     *
     * @param uid The ID of Student whose enrollment to be removed.
     * @param cid The ID of Course in which the student is enrolled in
     * @param months The number of months to add to the expiry date.
     * @return A ResponseEntity containing the updated StudentEnrollDTO. (HTTP status 200 OK)
     */
    @PutMapping("/enroll-changeExpiry/users/{userID}/course/{courseID}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentEnrollDTO> changeExpiryMonth(@PathVariable("userID") Integer uid,
                                                              @PathVariable ("courseID") Integer cid,
                                                              @RequestBody Integer months) {
        StudentEnrollDTO enrollDTO = this.enrollService.changeExpiryDateByMonth(uid, cid, months);
        return new ResponseEntity<>(enrollDTO, HttpStatus.OK);
    }

    /**
     * Removes a student's enrollment based on the provided User ID and Course ID.
     * <p>
     * DELETE /v4/enroll/users/{userID}/course/{courseUploadID}
     *
     * @param uid The ID of Student whose enrollment to be removed.
     * @param cid The ID of Course in which the student is enrolled in.
     * @return A ResponseEntity containing the ApiResponse indicating the success of the removal. (HTTP status 200 OK)
     */
    @DeleteMapping("/enroll/users/{userID}/course/{courseUploadID}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    public ResponseEntity<ApiResponse> removeEnrollment(@PathVariable("userID") Integer uid,
                                                        @PathVariable ("courseUploadID") Integer cid) {
        this.enrollService.removeEnrolledStudent(uid, cid);
        return new ResponseEntity<>(new ApiResponse("Enrollment Removed", true), HttpStatus.OK);
    }

}
