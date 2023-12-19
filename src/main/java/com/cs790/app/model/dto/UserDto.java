package com.cs790.app.model.dto;

import lombok.Data;

@Data
public class UserDto {

    private String id;
    private String name;

    private String email;

    private String password;

    private String phone;
}
