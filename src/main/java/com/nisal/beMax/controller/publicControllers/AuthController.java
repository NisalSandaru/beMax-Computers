package com.nisal.beMax.controller.publicControllers;

import com.nisal.beMax.exceptions.UserException;
import com.nisal.beMax.payload.request.LoginRequestDto;
import com.nisal.beMax.payload.request.SignUpRequestDto;
import com.nisal.beMax.payload.response.AuthResponse;
import com.nisal.beMax.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    //  http://localhost:5000/auth/signup

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUpHandler(
            @RequestBody SignUpRequestDto userDto
    ) throws UserException {
        return ResponseEntity.ok(authService.signup(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(
            @Valid @RequestBody LoginRequestDto userDto)throws UserException {
        return ResponseEntity.ok(authService.login(userDto));
    }

}
