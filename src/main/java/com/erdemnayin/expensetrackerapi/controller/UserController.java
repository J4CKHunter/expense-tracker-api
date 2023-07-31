package com.erdemnayin.expensetrackerapi.controller;

import com.erdemnayin.expensetrackerapi.dto.request.UserRequest;
import com.erdemnayin.expensetrackerapi.dto.request.UserWithTransactionsRequest;
import com.erdemnayin.expensetrackerapi.dto.response.UserResponse;
import com.erdemnayin.expensetrackerapi.dto.response.UserWithTransactionsResponse;
import com.erdemnayin.expensetrackerapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        System.out.println("test");
        return ResponseEntity.ok(userService.getAllUsersResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserResponseById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest){
        return ResponseEntity.ok(userService.createUser(userRequest));
    }

    // this is for the transactional scenario
    @PostMapping("/transactional")
    public ResponseEntity<UserWithTransactionsResponse> createUserWithTransactions(@Valid @RequestBody UserWithTransactionsRequest userWithTransactionsRequest){
        return ResponseEntity.ok(userService.createUserWithTransactions(userWithTransactionsRequest));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
//        return ResponseEntity.ok();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UserRequest userRequest, @PathVariable Long id){
        return ResponseEntity.ok(userService.updateUser(id, userRequest));
//        return ResponseEntity.ok();
    }

}
