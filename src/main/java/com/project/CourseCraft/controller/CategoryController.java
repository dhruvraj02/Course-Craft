package com.project.CourseCraft.controller;

import com.project.CourseCraft.dto.ApiResponse;
import com.project.CourseCraft.dto.CourseCategoryDTO;
import com.project.CourseCraft.service.CourseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/v2")
public class CategoryController {

    @Autowired
    CourseCategoryService categoryService;

    /**
     * Retrieves a list of all course categories.
     * <p>
     * GET /v2/category
     *
     * @return ResponseEntity with a list of CourseCategoryDTO objects and HTTP status 200 (OK)
     */
    @GetMapping("/category")
    public ResponseEntity<List<CourseCategoryDTO>> getCategories() {
        List<CourseCategoryDTO> courseCategories = categoryService.findAll();
        return new ResponseEntity<>(courseCategories, HttpStatus.OK);
    }

    /**
     * Retrieves a specific course category by its ID.
     * <p>
     * GET /v2/category/{categoryID}
     *
     * @param courseID The ID of the course category to retrieve.
     * @return ResponseEntity with the CourseCategoryDTO object of the specified category and HTTP status 200 (OK)
     */
    @GetMapping("/category/{categoryID}")
    public ResponseEntity<CourseCategoryDTO> getCategoryByID(@PathVariable("categoryID") Integer courseID) {
        CourseCategoryDTO categoryDTO = categoryService.findById(courseID);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    /**
     * Adds a new course category.
     * <p>
     * POST /v2/category
     *
     * @param categoryDTO The CourseCategoryDTO object containing the category details.
     * @return ResponseEntity with the created CourseCategoryDTO object and HTTP status 200 (OK)
     */
    @PostMapping("/category")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<CourseCategoryDTO> addCategory(@RequestBody CourseCategoryDTO categoryDTO) {
        CourseCategoryDTO newCategory = categoryService.addCourseCategory(categoryDTO);
        return new ResponseEntity<>(newCategory, HttpStatus.OK);
    }

    /**
     * Updates a specific course category.
     * <p>
     * PUT /v2/category/{categoryID}
     *
     * @param categoryDTO The CourseCategoryDTO object containing the updated category details.
     * @param courseID    The ID of the category to update.
     * @return ResponseEntity with the updated CourseCategoryDTO object and HTTP status 200 (OK)
     */
    @PutMapping("/category/{categoryID}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<CourseCategoryDTO> updateCategory(@RequestBody CourseCategoryDTO categoryDTO,
                                                            @PathVariable("categoryID") Integer courseID) {
        CourseCategoryDTO updatedCategory = categoryService.updateCourseCategory(categoryDTO, courseID);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    /**
     * Deletes a course category with the specified ID.
     * <p>
     * DELETE /v2/category/{categoryID}
     *
     * @param courseID The ID of the category to delete.
     * @return ResponseEntity with an ApiResponse object indicating the success of the deletion and HTTP status 200 (OK)
     */
    @DeleteMapping("/category/{categoryID}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryID") Integer courseID) {
        categoryService.deleteCourseCategory(courseID);
        return new ResponseEntity<>(new ApiResponse("Category Deleted", true), HttpStatus.OK);
    }
}
