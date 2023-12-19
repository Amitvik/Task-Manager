package com.cs790.app.utils;

import com.cs790.app.model.Pothole;
import com.cs790.app.model.User;
import com.cs790.app.model.dto.PotholeDto;
import com.cs790.app.model.dto.UserDto;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class DtoToEntityMapper {

    public String now(){
        LocalTime currentTime = LocalTime.now();

        // Define a DateTimeFormatter to format the time as a string (HH:mm:ss)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Format the time as a string
        String currentTimeString = currentTime.format(formatter);
        return currentTimeString;
    }

    public User UserDtoToUser(UserDto userDto){
        User user = new User();
        String id = UUID.randomUUID().toString();
        System.out.println(id);

        if (userDto.getId().equals("0"))
            user.setId(UUID.randomUUID().toString());
        else user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setPassword(userDto.getPassword());

        return user;

    }

    private Pothole PotholeDtoToPothole(PotholeDto potholeDto){
        Pothole pothole = new Pothole();
        pothole.setId(UUID.randomUUID().toString());
        pothole.setDateUploaded(now());
        pothole.setImageMetaData(potholeDto.getImageMetaData());
        pothole.setUserId(potholeDto.getUserId());
        pothole.setStreetName(potholeDto.getStreetName());

        return pothole;
    }
}
