package com.nisal.beMax.payload.response;

import com.nisal.beMax.payload.dto.UserDto;
import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private String message;
    private UserDto user;


}
