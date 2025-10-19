package com.nisal.beMax.payload.request;

import com.nisal.beMax.domain.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpRequestDto {

    @NotBlank(message = "fullName is required")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email")
    private String email;

    @NotBlank(message = "phone is required")
    private String phone;

    @NotBlank(message = "role is required")
    private UserRole role;

    @NotBlank(message = "Password is required")
    private String password;
}
