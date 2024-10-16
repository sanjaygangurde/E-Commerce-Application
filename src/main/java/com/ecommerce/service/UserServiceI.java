package com.ecommerce.service;

import com.ecommerce.dtos.UserDto;
import com.ecommerce.payloads.PageableResponse;

import java.util.List;

public interface UserServiceI {

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, String userId);

    void deleteUser(String userId);

    PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);

    UserDto getUserById(String userId);

    UserDto getUserByEmail(String userEmail);

    List<UserDto> searchUser(String keyword);


}
