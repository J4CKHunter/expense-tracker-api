package com.erdemnayin.expensetrackerapi.service;

import com.erdemnayin.expensetrackerapi.dto.request.LoginRequest;
import com.erdemnayin.expensetrackerapi.dto.request.RegisterRequest;
import com.erdemnayin.expensetrackerapi.dto.response.TokenResponse;
import com.erdemnayin.expensetrackerapi.dto.response.UserResponse;
import com.erdemnayin.expensetrackerapi.exception.GenericException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final TokenService tokenService;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 UserService userService,
                                 TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    public TokenResponse login(LoginRequest loginRequest){
        try{
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            return TokenResponse.builder()
                    .jwt(tokenService.generateToken(authentication))
                    .user(userService.getUserResponse(loginRequest.getEmail()))
                    .build();
        }catch (final BadCredentialsException badCredentialsException){
            throw new GenericException("Invalid username or password.", HttpStatus.BAD_REQUEST);
        }
    }

    public TokenResponse register(RegisterRequest registerRequest){
        try{


            // create new user
            UserResponse registeredUser = userService.registerUser(registerRequest);

            // login with new user
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(registeredUser.getEmail(), registerRequest.getPassword()));

            return TokenResponse.builder()
                    .jwt(tokenService.generateToken(authentication))
                    .user(userService.getUserResponse(registerRequest.getEmail()))
                    .build();
        }catch (final BadCredentialsException badCredentialsException){
            throw new GenericException("Invalid username or password.", HttpStatus.BAD_REQUEST);
        }
    }

    public UserResponse getAuthenticatedUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserResponse(username);
    }
}
