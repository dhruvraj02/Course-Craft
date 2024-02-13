package com.project.CourseCraft.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "videos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idvideos")
    private int videoID;

    @Column(name = "videoName")
    private String videoName;

    @Column(name = "videoLink")
    private String  videoLink;

    @ManyToOne
    @JoinColumn(name = "courseUploadId")
    private Staff staffCourse;

    @Override
    public String toString() {
        return "Video{" +
                "videoID=" + videoID +
                ", videoName='" + videoName + '\'' +
                ", videoLink='" + videoLink + '\'' +
                '}';
    }
}
