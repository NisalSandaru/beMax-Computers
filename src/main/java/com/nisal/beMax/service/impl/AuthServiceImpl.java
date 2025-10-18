package com.nisal.beMax.service.impl;

import com.nisal.beMax.configuration.JwtProvider;
import com.nisal.beMax.domain.UserRole;
import com.nisal.beMax.domain.validation.Util;
import com.nisal.beMax.exceptions.UserException;
import com.nisal.beMax.mapper.UserMapper;
import com.nisal.beMax.model.User;
import com.nisal.beMax.payload.dto.UserDto;
import com.nisal.beMax.payload.request.LoginRequestDto;
import com.nisal.beMax.payload.request.SignUpRequestDto;
import com.nisal.beMax.payload.response.AuthResponse;
import com.nisal.beMax.repository.UserRepository;
import com.nisal.beMax.service.AuthService;
import com.nisal.beMax.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserImplementation customUserImplementation;

    @Override
    public AuthResponse signup(SignUpRequestDto userDto) throws UserException {

        User user = userRepository.findByEmail(userDto.getEmail());
        if (user != null) {
            throw new UserException("Email already in use");
        }

        if (!Util.isMobileValid(userDto.getPhone())) {
            throw new UserException("phone number is not valid");
        }

        User newUser = new User();
        newUser.setFullName(userDto.getFullName());
        newUser.setEmail(userDto.getEmail());
        newUser.setPhone(userDto.getPhone());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if(userDto.getRole() == UserRole.ROLE_ADMIN) {
            newUser.setRole(UserRole.ROLE_ADMIN);
        }else {
            newUser.setRole(UserRole.ROLE_USER);
        }

        newUser.setLastLogin(LocalDateTime.now());

        User savedUser = userRepository.save(newUser);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Registered Successfully");
        authResponse.setUser(UserMapper.toDTO(savedUser));

        return authResponse;

    }

    @Override
    public AuthResponse login(LoginRequestDto userDto) throws UserException {
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        Authentication authentication = authenticate(email, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String role = authorities.iterator().next().getAuthority();

        String jwt = jwtProvider.generateToken(authentication);

        User user = userRepository.findByEmail(email);

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login Successfully");
        authResponse.setUser(UserMapper.toDTO(user));

        return authResponse;

    }

    private Authentication authenticate(String email, String password) throws UserException {

        UserDetails userDetails = customUserImplementation.loadUserByUsername(email);

        if(userDetails == null){
            throw new UserException("email id doesn't exist "+ email);
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new UserException("password doesn't match");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
