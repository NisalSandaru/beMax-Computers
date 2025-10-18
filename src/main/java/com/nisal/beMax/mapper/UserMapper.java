package com.nisal.beMax.mapper;

import com.nisal.beMax.model.User;
import com.nisal.beMax.payload.dto.UserDto;

public class UserMapper {

    public static UserDto toDTO(User savedUser) {
        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        userDto.setFullName(savedUser.getFullName());
        userDto.setEmail(savedUser.getEmail());
        userDto.setRole(savedUser.getRole());
        userDto.setCreatedAt(savedUser.getCreatedAt());
        userDto.setUpdatedAt(savedUser.getUpdatedAt());
        userDto.setLastLogin(savedUser.getLastLogin());
        userDto.setPhone(savedUser.getPhone());

        return userDto;
    }

    public static User toEntity(UserDto userDto) {
        User createdUser = new User();
        createdUser.setEmail(userDto.getEmail());
        createdUser.setFullName(userDto.getFullName());
        createdUser.setRole(userDto.getRole());
        createdUser.setCreatedAt(userDto.getCreatedAt());
        createdUser.setUpdatedAt(userDto.getUpdatedAt());
        createdUser.setLastLogin(userDto.getLastLogin());
        createdUser.setPhone(userDto.getPhone());
        createdUser.setPassword(userDto.getPassword());

        return createdUser;

    }

}
