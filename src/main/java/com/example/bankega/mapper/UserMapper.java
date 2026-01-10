package com.example.bankega.mapper;

import com.example.bankega.dto.UserDTO;
import com.example.bankega.entity.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }
}
