package com.project.CourseCraft.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Maps the roles available with an integer value
 */
@Entity
@Table(name = "user_role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRole {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "role_name")
    private String roleName;

}
