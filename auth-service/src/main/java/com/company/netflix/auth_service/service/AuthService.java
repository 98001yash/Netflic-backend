package com.company.netflix.auth_service.service;


import com.company.netflix.auth_service.dtos.SignupRequestDto;
import com.company.netflix.auth_service.dtos.UserResponseDto;
import com.company.netflix.auth_service.entity.User;
import com.company.netflix.auth_service.enums.Role;
import com.company.netflix.auth_service.exceptions.RuntimeConflictException;
import com.company.netflix.auth_service.repository.UserRepository;
import com.company.netflix.auth_service.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;


    public UserResponseDto signup(SignupRequestDto signupRequestDto){
        log.info("Attempting signup for email: {}",signupRequestDto.getEmail());

        if(userRepository.existsByEmail(signupRequestDto.getEmail())){
            log.warn("Signup failed. Email already exists: {}",signupRequestDto.getEmail());
            throw new RuntimeConflictException("Email already exists:");
        }

        // save user in auth_db
        User user = modelMapper.map(signupRequestDto, User.class);
        user.setPassword(PasswordUtils.hashPassword(signupRequestDto.getPassword()));
        user.setRole(Role.USER);
        user.setEnabled(true);
        user.setLocked(false);

        User savedUser = userRepository.save(user);
        log.info("User registered successfully with id: {}",savedUser.getId());

        return modelMapper.map(savedUser, UserResponseDto.class);
    }
}
