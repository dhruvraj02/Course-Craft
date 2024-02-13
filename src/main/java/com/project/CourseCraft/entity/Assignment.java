package com.project.CourseCraft.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "assignment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "aid")
    private int assignmentID;

    @Column(name = "assignmentName")
    private String assignmentName;

    @Column(name = "assignmentLink")
    private String assignmentLink;

    @ManyToOne
    @JoinColumn(name = "courseUploadId")
    private Staff staffCourse;

    @Override
    public String toString() {
        return "Assignment{" +
                "assignmentID=" + assignmentID +
                ", assignmentName='" + assignmentName + '\'' +
                '}';
    }
}
