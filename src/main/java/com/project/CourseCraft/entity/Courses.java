package com.project.CourseCraft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idcourse")
    private int courseID;

    @Column(name = "coursename")
    private String courseName;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @OneToMany(mappedBy = "courses", cascade = CascadeType.ALL)
    private List<Staff> staff = new ArrayList<>();

    @Override
    public String toString() {
        return "Courses{" +
                "courseID=" + courseID +
                ", courseName='" + courseName + '\'' +
                ", logo=" + Arrays.toString(logo) +
                '}';
    }
}
