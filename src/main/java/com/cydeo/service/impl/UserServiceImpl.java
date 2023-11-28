package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectService projectService;
    private final TaskService taskService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, @Lazy ProjectService projectService, TaskService taskService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @Override
    public List<UserDTO> listAllUsers() {

        List<User> userList = userRepository.findAll(Sort.by("firstName"));
        return userList.stream().map(userMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {

        User user = userRepository.findByUserName(username);

        return userMapper.convertToDTO(user);
    }

    @Override
    public void save(UserDTO userDTO) {

        userRepository.save(userMapper.convertToEntity(userDTO));
    }

    @Override
    public UserDTO update(UserDTO dto) {

        //Find current user
        User user = userRepository.findByUserName(dto.getUserName());
        //Convert this DTO to entity object
        User convertedUser = userMapper.convertToEntity(dto);
        //set id to converted object
        convertedUser.setId(user.getId());
        //save updated user
        userRepository.save(convertedUser);

        //this one doesn't capture the ID of the updating record:
//        userRepository.save(userMapper.convertToEntity(dto));

        return findByUserName(dto.getUserName());
    }

    @Override
    public void deleteByUserName(String username) {
        userRepository.deleteByUserName(username);
    }

    @Override
    public void delete(String username) {
        //this one will not delete from the DB, but just change the flag:

            //first bring the object
        User user = userRepository.findByUserName(username);
            //then set the isDeleted field to true
        if (checkIfUserCanBeDeleted(user)){
            user.setIsDeleted(true);
            user.setUserName(user.getUserName() + "-" + user.getId());
            userRepository.save(user);
        }
    }

    private boolean checkIfUserCanBeDeleted(User user){

        switch(user.getRole().getDescription()){
            case "Manager":
                List<ProjectDTO> projectDTOList= projectService.readAllByAssignedManager(user);
                return projectDTOList.size() == 0;
            case "Employee":
                List<TaskDTO> taskDTOList = taskService.readAllByAssignedEmployee(user);
                return taskDTOList.size() == 0;

            default:
                return true;
        }
    }

    @Override
    public List<UserDTO> listAllByRole(String role) {
        List<User> list = userRepository.findAllByRoleDescriptionIgnoreCase(role);
        return list.stream().map(userMapper::convertToDTO).collect(Collectors.toList());
    }

}
