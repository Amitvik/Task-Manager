package com.cs790.app.service.Impl;

import com.cs790.app.exceptions.UserAlreadyExistsException;
import com.cs790.app.exceptions.UserNotFoundException;
import com.cs790.app.model.User;
import com.cs790.app.model.dto.UserDto;
import com.cs790.app.repository.UserRepository;
import com.cs790.app.service.CloudWatchService;
import com.cs790.app.service.UserService;
import com.cs790.app.utils.DtoToEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private DtoToEntityMapper dtoToEntityMapper;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CloudWatchService service;

    @Override
    public User createUser(UserDto userDto) {
        User user = dtoToEntityMapper.UserDtoToUser(userDto);
        service.logMessageToCloudWatch("User with email: " + user.getEmail()+ " creation in progress");
        User user1 = userRepository.findByEmail(user.getEmail());

        if (user1 != null) {
            service.logMessageToCloudWatch("User already exists, cancelling operation");
            service.logMessageToCloudWatch("User not saved");
            throw new UserAlreadyExistsException("User with email: " + user.getEmail() + " already exists");
        }
        service.logMessageToCloudWatch("User created");
        return userRepository.save(user);
    }

    @Override
    public String processRegister(User user) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodedPassword);
        User user1 = userRepository.findByEmail(user.getEmail());
        if (user1 != null) {
            throw new UserAlreadyExistsException("User with email: " + user.getEmail() + " already exists");
        }
        userRepository.save(user);


        return "Registration success";
    }

    @Override
    public User processLogin(String email, String password) {
        User user = userRepository.findByEmail(email);
        service.logMessageToCloudWatch("User login in progress");
        if (user == null || !user.getPassword().equals(password)) {
            service.logMessageToCloudWatch("User not found for given credentials or incorrect");
            throw new UserNotFoundException("Invalid email or password");
        }
        service.logMessageToCloudWatch("User "+ user.getName() +" - "+user.getEmail() +" successfully logged in");
        return user;
    }

    @Override
    public String deleteUsers() {
        userRepository.deleteAll();
        return "All users deleted";
    }

}
