package com.project.CourseCraft.dto;

import lombok.*;

/**
 * Data Transfer Object (DTO) representing a video.
 *<p>
 * This DTO is used for transferring video data between different layers of the application.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VideoDTO {

    private int videoID;

    private String videoName;

    private String videoLink;

}
