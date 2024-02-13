package com.project.CourseCraft.service;

import com.project.CourseCraft.dto.ChangePasswordDTO;
import com.project.CourseCraft.dto.UserDTO;
import java.util.List;

/**
 *
 */
public interface UserService {

    public List<UserDTO> findAll();

    public UserDTO findByID(Integer id);

    public UserDTO findByUserID(String userID);

    public UserDTO registerNewUser(UserDTO userDTO);

    public UserDTO addAdmin(UserDTO userDTO);

    public UserDTO updateUser(UserDTO userDTO, Integer id);

    public void updatePassword(ChangePasswordDTO changePasswordDTO, Integer id);

    public void deleteUser(Integer id);
}
