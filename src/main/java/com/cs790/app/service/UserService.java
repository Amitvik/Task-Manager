package com.cs790.app.service;


import com.cs790.app.model.User;
import com.cs790.app.model.dto.UserDto;
import java.util.List;

public interface UserService {

    User createUser(UserDto userDto);

    String processRegister(User user);
    User processLogin(String email, String password);

    String deleteUsers();
}
