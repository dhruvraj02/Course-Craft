package com.project.CourseCraft.controller;

import com.project.CourseCraft.dto.*;
import com.project.CourseCraft.service.CourseDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/v5")
public class CourseDetailsController {

    @Autowired
    CourseDetailsService courseDetailsService;

    /**
     * Retrieves a list of assignments associated with a course upload.
     * <p>
     * HTTP Method: GET
     * <p>
     * URL: /v5/course/{cid}/assignment
     * <p>
     * HTTP Status: 200 OK
     *
     * @param cid The ID of the course upload.
     * @return ResponseEntity containing a list of AssignmentDTOs and the HTTP status.
     */
    @GetMapping("/course/{cid}/assignment")
    public ResponseEntity<List<AssignmentDTO>> getAssignment(@PathVariable("cid") Integer cid) {
        List<AssignmentDTO> DTOList = this.courseDetailsService.getAssignment(cid);
        return new ResponseEntity<>(DTOList, HttpStatus.OK);
    }

    /**
     * Retrieves a list of materials associated with a course upload.
     * <p>
     * HTTP Method: GET
     * <p>
     * URL: /v5/course/{cid}/material
     * <p>
     * HTTP Status: 200 OK
     *
     * @param cid The ID of the course upload.
     * @return ResponseEntity containing a list of MaterialDTOs and the HTTP status.
     */
    @GetMapping("/course/{cid}/material")
    public ResponseEntity<List<MaterialDTO>> getMaterial(@PathVariable("cid") Integer cid) {
        List<MaterialDTO> DTOList = this.courseDetailsService.getMaterial(cid);
        return new ResponseEntity<>(DTOList, HttpStatus.OK);
    }

    /**
     * Retrieves a list of videos associated with a course upload.
     * <p>
     * HTTP Method: GET
     * <p>
     * URL: /v5/course/{cid}/video
     * <p>
     * HTTP Status: 200 OK
     *
     * @param cid The ID of the course upload.
     * @return ResponseEntity containing a list of VideoDTOs and the HTTP status.
     */
    @GetMapping("/course/{cid}/video")
    public ResponseEntity<List<VideoDTO>> getVideo(@PathVariable("cid") Integer cid) {
        List<VideoDTO> DTOList = this.courseDetailsService.getVideo(cid);
        return new ResponseEntity<>(DTOList, HttpStatus.OK);
    }

    /**
     * Adds course details for a course upload.
     * <p>
     * HTTP Method: POST
     * <p>
     * URL: /v5/course/{cid}/course-details
     * <p>
     * HTTP Status: 200 OK
     *
     * @param courseDetailsDTO The CourseDetailsDTO containing the course details information.
     * @param cid              The ID of the course upload.
     * @return ResponseEntity with a success message and the HTTP status.
     */
    @PostMapping("/course/{cid}/course-details")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<ApiResponse> addCourseDetails(@RequestBody CourseDetailsDTO courseDetailsDTO,
                                                        @PathVariable("cid") Integer cid) {
        this.courseDetailsService.addCourseDetails(courseDetailsDTO, cid);
        return new ResponseEntity<>(new ApiResponse("Course Details Added", true), HttpStatus.OK);
    }

    /**
     * Adds an assignment for a course upload.
     * <p>
     * HTTP Method: POST
     * <p>
     * URL: /v5/course/{cid}/course-details/assignment
     * <p>
     * HTTP Status: 200 OK
     *
     * @param assignmentDTO The AssignmentDTO containing the assignment information.
     * @param cid           The ID of the course upload.
     * @return ResponseEntity with a success message and the HTTP status.
     */
    @PostMapping("/course/{cid}/course-details/assignment")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<ApiResponse> addAssignment(@RequestBody AssignmentDTO assignmentDTO,
                                                     @PathVariable("cid") Integer cid) {
        this.courseDetailsService.addAssignment(assignmentDTO, cid);
        return new ResponseEntity<>(new ApiResponse("Assignment Added", true), HttpStatus.OK);
    }

    /**
     * Adds a material for a course upload.
     * <p>
     * HTTP Method: POST
     * <p>
     * URL: /v5/course/{cid}/course-details/material
     * <p>
     * HTTP Status: 200 OK
     *
     * @param materialDTO The MaterialDTO containing the material information.
     * @param cid         The ID of the course upload.
     * @return ResponseEntity with a success message and the HTTP status.
     */
    @PostMapping("/course/{cid}/course-details/material")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<ApiResponse> addMaterial(@RequestBody MaterialDTO materialDTO,
                                                   @PathVariable("cid") Integer cid) {
        this.courseDetailsService.addMaterial(materialDTO, cid);
        return new ResponseEntity<>(new ApiResponse("Material Added", true), HttpStatus.OK);
    }

    /**
     * Adds a video for a course upload.
     * <p>
     * HTTP Method: POST
     * <p>
     * URL: /v5/course/{cid}/course-details/video
     * <p>
     * HTTP Status: 200 OK
     *
     * @param videoDTO The VideoDTO containing the video information.
     * @param cid      The ID of the course upload.
     * @return ResponseEntity with a success message and the HTTP status.
     */
    @PostMapping("/course/{cid}/course-details/video")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<ApiResponse> addVideo(@RequestBody VideoDTO videoDTO,
                                                @PathVariable("cid") Integer cid) {
        this.courseDetailsService.addVideo(videoDTO, cid);
        return new ResponseEntity<>(new ApiResponse("Video Added", true), HttpStatus.OK);
    }

    /**
     * Updates an assignment associated with a course upload.
     * <p>
     * HTTP Method: PUT
     * <p>
     * URL: /v5/assignment/{aid}
     * <p>
     * HTTP Status: 200 OK
     *
     * @param assignmentDTO The AssignmentDTO containing the updated assignment information.
     * @param aid           The ID of the assignment.
     * @return ResponseEntity with a success message and the HTTP status.
     */
    @PutMapping("/assignment/{aid}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<ApiResponse> updateAssignment(@RequestBody AssignmentDTO assignmentDTO,
                                                        @PathVariable("aid") Integer aid) {
        this.courseDetailsService.updateAssignment(assignmentDTO, aid);
        return new ResponseEntity<>(new ApiResponse("Assignment updated", true), HttpStatus.OK);
    }

    /**
     * Updates a material associated with a course upload.
     * <p>
     * HTTP Method: PUT
     * <p>
     * URL: /v5/material/{mid}
     * <p>
     * HTTP Status: 200 OK
     *
     * @param materialDTO The MaterialDTO containing the updated material information.
     * @param mid         The ID of the material.
     * @return ResponseEntity with a success message and the HTTP status.
     */
    @PutMapping("/material/{mid}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<ApiResponse> updateMaterial(@RequestBody MaterialDTO materialDTO,
                                                      @PathVariable("mid") Integer mid) {
        this.courseDetailsService.updateMaterial(materialDTO, mid);
        return new ResponseEntity<>(new ApiResponse("Material updated", true), HttpStatus.OK);
    }

    /**
     * Updates a video associated with a course upload.
     * <p>
     * HTTP Method: PUT
     * <p>
     * URL: /v5/video/{vid}
     * <p>
     * HTTP Status: 200 OK
     *
     * @param videoDTO The VideoDTO containing the updated video information.
     * @param vid      The ID of the video.
     * @return ResponseEntity with a success message and the HTTP status.
     */
    @PutMapping("/video/{vid}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<ApiResponse> updateVideo(@RequestBody VideoDTO videoDTO,
                                                   @PathVariable("vid") Integer vid) {
        this.courseDetailsService.updateVideo(videoDTO, vid);
        return new ResponseEntity<>(new ApiResponse("Video updated", true), HttpStatus.OK);
    }

    /**
     * Deletes an assignment associated with a course upload.
     * <p>
     * HTTP Method: DELETE
     * <p>
     * URL: /v5/assignment/{id}
     * <p>
     * HTTP Status: 200 OK
     *
     * @param id The ID of the assignment.
     * @return ResponseEntity with a success message and the HTTP status.
     */
    @DeleteMapping("/assignment/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<ApiResponse> deleteAssignment(@PathVariable("id") Integer id) {
        this.courseDetailsService.deleteAssignment(id);
        return new ResponseEntity<>(new ApiResponse("Assignment Deleted", true), HttpStatus.OK);
    }

    /**
     * Deletes a material associated with a course upload.
     * <p>
     * HTTP Method: DELETE
     * <p>
     * URL: /v5/material/{id}
     * <p>
     * HTTP Status: 200 OK
     *
     * @param id The ID of the material.
     * @return ResponseEntity with a success message and the HTTP status.
     */
    @DeleteMapping("/material/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<ApiResponse> deleteMaterial(@PathVariable("id") Integer id) {
        this.courseDetailsService.deleteMaterial(id);
        return new ResponseEntity<>(new ApiResponse("Material Deleted", true), HttpStatus.OK);
    }

    /**
     * Deletes a video associated with a course upload.
     *
     * <p>HTTP Method: DELETE</p>
     *
     * <p>URL: /v5/video/{id}</p>
     *
     * <p>HTTP Status: 200 OK</p>
     *
     * @param id The ID of the video.
     * @return ResponseEntity with a success message and the HTTP status.
     */

    @DeleteMapping("/video/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<ApiResponse> deleteVideo(@PathVariable("id") Integer id) {
        this.courseDetailsService.deleteVideo(id);
        return new ResponseEntity<>(new ApiResponse("Video Deleted", true), HttpStatus.OK);
    }
}