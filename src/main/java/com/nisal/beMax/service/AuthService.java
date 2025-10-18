package com.nisal.beMax.service;

import com.nisal.beMax.exceptions.UserException;
import com.nisal.beMax.payload.dto.UserDto;
import com.nisal.beMax.payload.request.LoginRequestDto;
import com.nisal.beMax.payload.request.SignUpRequestDto;
import com.nisal.beMax.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse signup(SignUpRequestDto userDto) throws UserException;
    AuthResponse login(LoginRequestDto userDto) throws UserException;
}
