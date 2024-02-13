package com.project.CourseCraft.service;

import com.project.CourseCraft.config.AppConstants;
import com.project.CourseCraft.dao.UserRepository;
import com.project.CourseCraft.dao.UserRoleRepository;
import com.project.CourseCraft.dto.ChangePasswordDTO;
import com.project.CourseCraft.dto.UserDTO;
import com.project.CourseCraft.entity.User;
import com.project.CourseCraft.entity.UserRole;
import com.project.CourseCraft.exception.ApiException;
import com.project.CourseCraft.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleRepository roleRepository;


    /**
     * Retrieves a list of all the users.
     *
     * @return List containing all users
     */
    @Override
    public List<UserDTO> findAll() {
        // Retrieve all users from the database
        List<User> userList = userRepository.findAll();

        // Create a list to hold the user DTOs
        List<UserDTO> userDTOList = new ArrayList<>();
        UserDTO userDTO;

        int i = 0;
        // Iterate through the user list and convert each user to DTO
        while (i < userList.size()) {
            userDTO = mapper(userList.get(i));
            userDTOList.add(userDTO);
            i++;
        }

        return userDTOList;
    }


    /**
     * Retrieves a user by their primary key.
     *
     * @param id the ID of the user to retrieve
     * @return the user DTO
     * @throws ResourceNotFoundException if the user is not found
     */
    @Override
    public UserDTO findByID(Integer id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "User ID", 0)
        );
        return mapper(user);
    }

    /**
     * Retrieves a user by their user ID.
     *
     * @param userID the user ID to search for
     * @return the user DTO
     * @throws ResourceNotFoundException if the user is not found
     */
    @Override
    public UserDTO findByUserID(String userID) {
        User user = userRepository.findByUserID(userID).orElseThrow(
                () -> new ResourceNotFoundException("User", "User ID", 0)
        );
        System.out.println(user);
        return mapper(user);
    }

    /**
     * Registers a new staff or student.
     *
     * @param userDTO the user DTO to register
     * @return the registered user DTO
     */
    @Override
    public UserDTO registerNewUser(UserDTO userDTO) {
        // Map user DTO to User entity
        User user = mapper(userDTO);

        // Encode the user's password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Determine user role and assign it
        if (userDTO.getRole().equalsIgnoreCase("student")) {
            // Assign "student" role
            UserRole role = roleRepository.findById(AppConstants.STUDENT_USER).get();
            user.getUserRoles().add(role);
            user.setUserID("STU" + AppConstants.autoUserID);
        } else if (userDTO.getRole().equalsIgnoreCase("staff")) {
            // Assign "staff" role
            UserRole role = roleRepository.findById(AppConstants.STAFF_USER).get();
            user.getUserRoles().add(role);
            user.setUserID("STF" + AppConstants.autoUserID);
        } else {
            // Invalid user role provided
            throw new ApiException("Invalid User Role !!");
        }

        // Save the new user in the database
        User newUser = userRepository.save(user);

        return mapper(newUser);
    }


    /**
     * Adds a new admin user.
     *
     * @param userDTO the admin user DTO to add
     * @return the added admin user DTO
     */
    @Override
    public UserDTO addAdmin(UserDTO userDTO) {
        // Map user DTO to User entity
        User user = mapper(userDTO);

        // Assign "admin" role
        UserRole role = roleRepository.findById(AppConstants.ADMIN_USER).get();
        user.getUserRoles().add(role);
        // Sets a unique userID
        user.setUserID("ADM" + AppConstants.autoUserID);

        // Save the new admin user in the database
        User savedUser = userRepository.save(user);

        return mapper(savedUser);
    }


    /**
     * Updates a user.
     *
     * @param userDTO the user DTO with updated data
     * @param id      the ID of the user to update
     * @return the updated user DTO
     * @throws ResourceNotFoundException if the user is not found
     */
    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "User ID", 0)
        );
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        userRepository.save(user);

        return mapper(user);
    }

    /**
     * Changes Password
     *
     * @param changePasswordDTO Password DTO
     * @param id The ID of the user
     * @throws ResourceNotFoundException if the user isn't found
     */
    @Override
    public void updatePassword(ChangePasswordDTO changePasswordDTO, Integer id) {

        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "User ID", 0)
        );
        user.setPassword(this.passwordEncoder.encode(changePasswordDTO.getPassword()));
        userRepository.save(user);
    }

    /**
     * Deletes a user.
     *
     * @param id the ID of the user to delete
     * @throws ResourceNotFoundException if the user is not found
     */
    @Override
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "User ID", id)
        );

        userRepository.delete(user);
    }

    /**
     * Populates a User object from UserDTO.
     *
     * @param userDTO the UserDTO to map from
     * @return the mapped User object
     */
    private User mapper(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUserID(userDTO.getUserID());
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    /**
     * Populates UserDTO object from User.
     *
     * @param user the User to map from
     * @return the mapped UserDTO object
     */
    private UserDTO mapper(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUserID(user.getUserID());
        userDTO.setFullName(user.getFullName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getUserRoles().stream().findFirst().orElse(null).getRoleName().substring(5));
        return userDTO;
    }
}
