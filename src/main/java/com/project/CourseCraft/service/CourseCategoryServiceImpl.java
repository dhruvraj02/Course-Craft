package com.project.CourseCraft.service;

import com.project.CourseCraft.dao.CourseCategoryRepository;
import com.project.CourseCraft.dto.CourseCategoryDTO;
import com.project.CourseCraft.entity.Courses;
import com.project.CourseCraft.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseCategoryServiceImpl implements CourseCategoryService{

    @Autowired
    CourseCategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    /**
     * Retrieves all course categories.
     *
     * @return the list of course categories
     * @throws ResourceNotFoundException if no course categories are found
     */
    @Override
    public List<CourseCategoryDTO> findAll() {
        // Retrieve all courses from the repository
        List<Courses> courses = this.categoryRepository.findAll();

        // Create a list to hold the course category DTOs
        List<CourseCategoryDTO> categoryDTOList = new ArrayList<>();

        // Check if there are no courses found
        if (courses == null) {
            throw new ResourceNotFoundException("Course Category", "any", 0);
        }

        // Map each course to its corresponding DTO and add it to the list
        for (Courses course : courses) {
            // Map the course to its DTO using the model mapper
            CourseCategoryDTO courseCategoryDTO = modelMapper.map(course, CourseCategoryDTO.class);

            // Add the DTO to the list
            categoryDTOList.add(courseCategoryDTO);
        }
        
        return categoryDTOList;
    }


    /**
     * Retrieves a course category by its ID.
     *
     * @param courseID the ID of the course category to retrieve
     * @return the course category DTO
     * @throws ResourceNotFoundException if the course category is not found
     */
    @Override
    public CourseCategoryDTO findById(Integer courseID) {
        Courses courses = this.categoryRepository.findById(courseID).orElseThrow(
                ()-> new ResourceNotFoundException("Course Category", "id", courseID));
        return this.modelMapper.map(courses,CourseCategoryDTO.class);
    }

    /**
     * Adds a new course category.
     *
     * @param categoryDTO the course category DTO to add
     * @return the added course category DTO
     */
    @Override
    public CourseCategoryDTO addCourseCategory(CourseCategoryDTO categoryDTO) {
        Courses courses = this.modelMapper.map(categoryDTO, Courses.class);
        Courses newCourse = this.categoryRepository.save(courses);

        return this.modelMapper.map(newCourse, CourseCategoryDTO.class);
    }

    /**
     * Updates a course category.
     *
     * @param categoryDTO the course category DTO with updated data
     * @param courseID    the ID of the course category to update
     * @return the updated course category DTO
     * @throws ResourceNotFoundException if the course category is not found
     */
    @Override
    public CourseCategoryDTO updateCourseCategory(CourseCategoryDTO categoryDTO, Integer courseID) {
        Courses courses = this.categoryRepository.findById(courseID).orElseThrow(
                ()-> new ResourceNotFoundException("Course category","id",courseID)
        );
        courses.setCourseName(categoryDTO.getCourseName());
        courses.setLogo(categoryDTO.getLogo());
        Courses updatedCourse = this.categoryRepository.save(courses);
        return this.modelMapper.map(updatedCourse,CourseCategoryDTO.class);
    }

    /**
     * Deletes a course category by its ID.
     *
     * @param courseID the ID of the course category to delete
     * @throws ResourceNotFoundException if the course category is not found
     */
    @Override
    public void deleteCourseCategory(Integer courseID) {
        Courses courses = this.categoryRepository.findById(courseID).orElseThrow(
                ()-> new ResourceNotFoundException("Course category","id",courseID)
        );
        this.categoryRepository.delete(courses);
    }
}
