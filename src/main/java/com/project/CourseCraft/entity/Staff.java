package com.project.CourseCraft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Mapping the staff to the
 * courses they have added
 */
@Entity
@Table(name = "staff")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Cacheable
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CourseUploadId")
    private Integer courseUploadID;

    @Column(name = "CourseUpName")
    private String courseUploadName;

    @Column(name = "Upload_date")
    private Date uploadDate;

    @Column(name = "Disable_date")
    private Date disableDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "idcourse")
    private Courses courses;

    @OneToMany(mappedBy = "staffCourse", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Student> student = new ArrayList<>();

    @OneToMany(mappedBy = "staffCourse", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Assignment> assignment = new ArrayList<>();

    @OneToMany(mappedBy = "staffCourse", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Material> material = new ArrayList<>();

    @OneToMany(mappedBy = "staffCourse", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Video> video = new ArrayList<>();

    @Override
    public String toString() {
        return "Staff{" +
                "courseUploadID=" + courseUploadID +
                ", courseUploadName='" + courseUploadName + '\'' +
                ", uploadDate=" + uploadDate +
                ", disableDate=" + disableDate +
                ", courses=" + courses +
                '}';
    }
}
