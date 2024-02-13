package com.project.CourseCraft.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.Date;

/**
 * Mapping students to the courses they have enrolled
 */
@Entity
@Table(name = "student")
@IdClass(Student.StudentPK.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Column(name = "Enroll_date")
    private Date enrollDate;

    @Column(name = "Expiry_date")
    private Date expiryDate;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "CourseUploadId")
    private Staff staffCourse;

    @Override
    public String toString() {
        return "Student{" +
                ", enrollDate=" + enrollDate +
                ", expiryDate=" + expiryDate +
                '}';
    }

    public static class StudentPK implements Serializable {
        private User user;
        private Staff staffCourse;
    }
}
