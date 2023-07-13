package com.erdemnayin.expensetrackerapi.service;

import com.erdemnayin.expensetrackerapi.dto.request.RegisterRequest;
import com.erdemnayin.expensetrackerapi.dto.request.TransactionRequest;
import com.erdemnayin.expensetrackerapi.dto.request.UserRequest;
import com.erdemnayin.expensetrackerapi.dto.request.UserWithTransactionsRequest;
import com.erdemnayin.expensetrackerapi.dto.response.UserResponse;
import com.erdemnayin.expensetrackerapi.dto.response.UserWithTransactionsResponse;
import com.erdemnayin.expensetrackerapi.exception.GenericException;
import com.erdemnayin.expensetrackerapi.exception.UserNotFoundException;
import com.erdemnayin.expensetrackerapi.model.Role;
import com.erdemnayin.expensetrackerapi.model.User;
import com.erdemnayin.expensetrackerapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new GenericException("User not found with the email: " + email, HttpStatus.NOT_FOUND));
    }

    public UserResponse getUserResponse(String email){
        var user = findUserByEmail(email);

        return UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public UserResponse registerUser(RegisterRequest registerRequest){

        final User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);


        return UserResponse.builder()
                .id(savedUser.getId())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .build();
    }

    public UserResponse createUser(UserRequest userRequest){

        final User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(userRequest.getRole());

        User savedUser = userRepository.save(user);


        return UserResponse.builder()
                .id(savedUser.getId())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .build();
    }

    public List<UserResponse> getAllUsersResponse() {

        return userRepository.findAll().stream()
                .map(UserService::convertUserResponse)
                .toList();
    }

    public List<User> getAllUsers(){
        return userRepository.findAll().stream().toList();
    }

    private static UserResponse convertUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
    }

    public UserResponse getUserResponseById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found by given id."));

        return convertUserResponse(user);
    }

    public User getUserById(Long id){

        return userRepository.findById(id)
                .orElseThrow(() -> new GenericException("User not found by given id.", HttpStatus.NOT_FOUND));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserResponse updateUser(Long id, UserRequest userRequest) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new GenericException("User not found by given id.", HttpStatus.NOT_FOUND));


        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(userRequest.getRole());

        User savedUser = userRepository.save(user);


        return UserResponse.builder()
                .id(savedUser.getId())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .build();
    }


    // this is for the transactional scenario
    @Transactional
    public UserWithTransactionsResponse createUserWithTransactions(UserWithTransactionsRequest userWithTransactionsRequest) {

        final User user = new User();
        user.setFirstName(userWithTransactionsRequest.getFirstName());
        user.setLastName(userWithTransactionsRequest.getLastName());
        user.setEmail(userWithTransactionsRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userWithTransactionsRequest.getPassword()));
        user.setRole(userWithTransactionsRequest.getRole());
        user.setTransactions(new ArrayList<>());

        for (TransactionRequest transactionRequest : userWithTransactionsRequest.getTransactionRequests()) {
            user.getTransactions().add(TransactionService.convertResponse(transactionRequest));
            throw new DataIntegrityViolationException("Throwing exception for demoing Rollback!!!");
        }

        return null;

    }
}
