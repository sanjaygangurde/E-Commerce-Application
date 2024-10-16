package com.ecommerce.controller;

import com.ecommerce.dtos.UserDto;
import com.ecommerce.payloads.ApiResponse;
import com.ecommerce.payloads.PageableResponse;
import com.ecommerce.service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceI userServiceI;


    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto userDto1 = userServiceI.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable String userId, @RequestBody UserDto userDto) {
        UserDto updateUser = userServiceI.updateUser(userDto, userId);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
        userServiceI.deleteUser(userId);

        ApiResponse response = ApiResponse.builder().message("User Deleted Successfully").success(true).status(HttpStatus.OK).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber
            , @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy
            , @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        return new ResponseEntity<>(userServiceI.getAllUser(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId) {
        return new ResponseEntity<>(userServiceI.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userServiceI.getUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords) {
        return new ResponseEntity<>(userServiceI.searchUser(keywords), HttpStatus.OK);
    }


}
