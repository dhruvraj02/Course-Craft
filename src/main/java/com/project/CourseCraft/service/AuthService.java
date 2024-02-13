package com.project.CourseCraft.service;

import com.project.CourseCraft.dto.JwtAuthRequest;
import com.project.CourseCraft.dto.JwtAuthResponse;

public interface AuthService {

    JwtAuthResponse login(JwtAuthRequest request);

}
