package com.project.CourseCraft.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "material")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mid")
    private int materialID;

    @Column(name = "mlink")
    private String materialLink;

    @ManyToOne
    @JoinColumn(name = "courseUploadId")
    private Staff staffCourse;

    @Override
    public String toString() {
        return "Material{" +
                "materialID=" + materialID +
                ", materialLink='" + materialLink + '\'' +
                '}';
    }

}
