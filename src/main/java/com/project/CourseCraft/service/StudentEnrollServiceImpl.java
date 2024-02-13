package com.project.CourseCraft.service;

import com.project.CourseCraft.dao.StaffCourseRepository;
import com.project.CourseCraft.dao.StudentEnrollRepository;
import com.project.CourseCraft.dao.UserRepository;
import com.project.CourseCraft.dto.CourseAllDTO;
import com.project.CourseCraft.dto.StudentCourseEnrollDTO;
import com.project.CourseCraft.dto.StudentEnrollDTO;
import com.project.CourseCraft.entity.Staff;
import com.project.CourseCraft.entity.Student;
import com.project.CourseCraft.entity.User;
import com.project.CourseCraft.exception.ApiException;
import com.project.CourseCraft.exception.ResourceNotFoundException;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service implementation for managing student enrollments.
 */
@Service
public class StudentEnrollServiceImpl implements StudentEnrollService {

    @Autowired
    StudentEnrollRepository enrollRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StaffCourseRepository courseRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    EntityManager entityManager;

    Calendar calendar = Calendar.getInstance();

    /**
     * Retrieves the list of courses enrolled by a user.
     *
     * @param uid The ID of the user.
     * @return List of CourseAllDTO objects representing the enrolled courses.
     * @throws ResourceNotFoundException if the user or courses are not found.
     * @throws ApiException if the user is not a student.
     */
    @Override
    public List<StudentCourseEnrollDTO> getEnrollByUser(Integer uid) {

        // Retrieve the user with the given user ID
        User user = this.userRepository.findById(uid).orElseThrow(
                () -> new ResourceNotFoundException("User", "User ID", uid)
        );

        // Check if the user enrolling request is for a student or any other user
        if (user.getUserID().substring(0, 3).equalsIgnoreCase("ADM") || user.getUserID().substring(0, 3).equalsIgnoreCase("STF")) {
            throw new ApiException("User is not a student!");
        }

        // Retrieve the list of students enrolled by the user
        List<Student> studentList = this.enrollRepository.findByUser(user);

        List<Staff> courses = new ArrayList<>();
        List<Date> enrollDateList = new ArrayList<>();
        List<Date> expiryDateList = new ArrayList<>();

        // Iterate over each enrolled student
        for (Student student : studentList) {
            try {
                // Retrieve the corresponding course for the student
                Staff course = this.courseRepository.findById(student.getStaffCourse().getCourseUploadID()).orElseThrow(
                        () -> new ResourceNotFoundException("Course", "id", student.getStaffCourse().getCourseUploadID())
                );
                courses.add(course);
                enrollDateList.add(student.getEnrollDate());
                expiryDateList.add(student.getExpiryDate());
            } catch (ResourceNotFoundException ignored) {
            }
        }

        List<StudentCourseEnrollDTO> enrollDTOList = new ArrayList<>();

        // Maps each course to CourseAllDTO
        for (Staff course : courses) {
            StudentCourseEnrollDTO enrollDTO = this.modelMapper.map(course, StudentCourseEnrollDTO.class);
            enrollDTO.setCategoryName(course.getCourses().getCourseName());
            enrollDTO.setUserName(course.getUser().getFullName());
            // Add the CourseAllDTO to the list
            enrollDTOList.add(enrollDTO);
        }

        // Maps the enrolled and expiry date of a particular course
        for(int i = 0; i < enrollDTOList.size(); i++){
            enrollDTOList.get(i).setEnrollDate(String.valueOf(enrollDateList.get(i)).substring(0,10));
            enrollDTOList.get(i).setExpiryDate(String.valueOf(expiryDateList.get(i)).substring(0,10));
        }

        return enrollDTOList;
    }

    /**
     * Retrieves a list of enrolled students based on the provided staff course ID.
     *
     * @param courseID The ID of the staff course.
     * @return A list of StudentEnrollDTO representing the enrolled students.
     * @throws ResourceNotFoundException If the staff course with the given ID is not found.
     */
    @Override
    public List<StudentEnrollDTO> getEnrollByStaffCourse(Integer courseID) {

        // Retrieve the staff course with the given staff course ID
        Staff staff = this.courseRepository.findById(courseID).orElseThrow(
                () -> new ResourceNotFoundException("Course ", " course Id ", courseID)
        );

        List<Student> studentList = this.enrollRepository.findByStaffCourse(staff);

        return mapper(studentList);
    }


    /**
     * Enrolls a student in a staff course.
     *
     * @param uid           the ID of the user to enroll
     * @param staffCourseID the ID of the staff course to enroll in
     * @return the enrolled student DTO
     * @throws ResourceNotFoundException if the user or staff course is not found
     */
    @Override
    public StudentEnrollDTO enrollStudent(Integer uid, Integer staffCourseID) {
        // Retrieve the user with the given user ID
        User user = this.userRepository.findById(uid).orElseThrow(
                () -> new ResourceNotFoundException("User", "User ID", uid)
        );

        // Check if the user enrolling request is for a student or any other user
        if (user.getUserID().substring(0, 3).equalsIgnoreCase("ADM") || user.getUserID().substring(0, 3).equalsIgnoreCase("STF")) {
            throw new ApiException("User not a student!");
        }

        // Retrieve the staff course with the given staff course ID
        Staff staff = this.courseRepository.findById(staffCourseID).orElseThrow(
                () -> new ResourceNotFoundException("Course ", " course Id ", staffCourseID)
        );

        // Create a new student enrollment
        Student enrollStudent = new Student();

        // Set the user and staff course for the enrollment
        enrollStudent.setUser(user);
        enrollStudent.setStaffCourse(staff);

        // Set the enrollment date to the current date
        enrollStudent.setEnrollDate(new Date());

        // Calculate and set the expiry date to 3 months after the enrollment date
        calendar.setTime(enrollStudent.getEnrollDate());
        calendar.add(Calendar.MONTH, 3);
        enrollStudent.setExpiryDate(calendar.getTime());

        // Save the enrolled student
        Student enrolledStudent = this.enrollRepository.save(enrollStudent);

        // Map the enrolled student to its DTO and return it
        return mapper(enrolledStudent);
    }


    /**
     * Removes the enrollment of a student in a staff course based on the provided user ID and course ID.
     *
     * @param uid      The ID of the user associated with the student.
     * @param courseID The ID of the staff course from which the student's enrollment should be removed.
     * @throws ResourceNotFoundException If the user or staff course is not found, or if the user is not a student.
     */
    @Override
    public void removeEnrolledStudent(Integer uid, Integer courseID) {

        Student student = findStudentByUserIdAndCourseId(uid, courseID);

        if(student.getStaffCourse() == null) {
            throw new ResourceNotFoundException("Enrollment of User" , "course ID", courseID);
        }
        this.enrollRepository.delete(student);

    }


    /**
     * Changes the expiry date of a student's enrollment in a staff course by a specified number of months.
     *
     * @param uid      The ID of the user associated with the student.
     * @param courseID The ID of the staff course for which the student's enrollment should be updated.
     * @param months   The number of months by which to change the expiry date.
     * @return The updated StudentEnrollDTO representing the student's enrollment details.
     * @throws ResourceNotFoundException If the user or staff course is not found, or if the user is not a student.
     */
    @Override
    public StudentEnrollDTO changeExpiryDateByMonth(Integer uid, Integer courseID, Integer months) {

        Student student = findStudentByUserIdAndCourseId(uid, courseID);

        // Update the expiry date by adding the specified number of months
        calendar.setTime(student.getExpiryDate());
        calendar.add(Calendar.MONTH, months);

        student.setExpiryDate(calendar.getTime());

        // Save the updated student's enrollment
        Student updatedStudent = this.enrollRepository.save(student);

        // Map the updated student's enrollment to StudentEnrollDTO
        return mapper(updatedStudent);
    }



    /**
     * Maps a Student object to a StudentEnrollDTO object.
     *
     * @param student The Student object to be mapped.
     * @return The mapped StudentEnrollDTO object.
     */
    private StudentEnrollDTO mapper(Student student) {
        StudentEnrollDTO enrollDTO = new StudentEnrollDTO();

        // Set enroll date from the student object
        enrollDTO.setEnrollDate(student.getEnrollDate());

        // Set expiry date from the student object
        enrollDTO.setExpiryDate(student.getExpiryDate());

        // Set username from the user associated with the student
        enrollDTO.setUserName(student.getUser().getFullName());

        return enrollDTO;
    }

    /**
     * Maps a list of Student objects to a list of StudentEnrollDTO objects.
     *
     * @param studentList The list of Student objects to be mapped.
     * @return The mapped list of StudentEnrollDTO objects.
     */
    private List<StudentEnrollDTO> mapper(List<Student> studentList) {
        List<StudentEnrollDTO> enrollDTOList = new ArrayList<>();

        for (Student student : studentList) {
            // Map each Student object to a StudentEnrollDTO object
            enrollDTOList.add(mapper(student));
        }

        return enrollDTOList;
    }

    /**
     * Finds and returns the enrolled student based on the provided user ID and staff course ID.
     *
     * @param uid      The ID of the user associated with the student.
     * @param courseID The ID of the staff course associated with the student.
     * @return The enrolled Student object.
     * @throws ResourceNotFoundException If the user or staff course is not found, or if the user is not a student.
     */
    private Student findStudentByUserIdAndCourseId(Integer uid, Integer courseID) {
        Student student = new Student();

        // Retrieve the user with the given user ID
        User user = this.userRepository.findById(uid).orElseThrow(
                () -> new ResourceNotFoundException("User", "User ID", uid)
        );

        // Check if the user enrolling request is for a student or any other user
        if (user.getUserID().substring(0, 3).equalsIgnoreCase("ADM") || user.getUserID().substring(0, 3).equalsIgnoreCase("STF")) {
            throw new ApiException("User not a student!");
        }

        // Retrieve the staff course with the given staff course ID
        Staff staff = this.courseRepository.findById(courseID).orElseThrow(
                () -> new ResourceNotFoundException("Course ", " course Id ", courseID)
        );

        // Find the student's enrollment by the provided enrollID
        List<Student> studentList = this.enrollRepository.findByUser(user);

        for (Student studentByUser : studentList) {
            if (studentByUser.getStaffCourse() == staff) {
                student = studentByUser;
                break;
            }
        }
        return student;
    }

}
