package com.ecommerce.service.impl;

import com.ecommerce.dtos.UserDto;
import com.ecommerce.entities.User;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.helper.Helper;
import com.ecommerce.payloads.PageableResponse;
import com.ecommerce.repository.UserRepo;
import com.ecommerce.service.UserServiceI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserServiceI {


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper mapper;


    @Override
    public UserDto createUser(UserDto userDto) {
        // generating random userId
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        //dto-> entity
        User user = this.dtoToEntity(userDto);
        User savedUser = this.userRepo.save(user);

        // returned entity-> dto
        return this.entityToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setUserName(userDto.getUserName());
        user.setUserAbout(userDto.getUserAbout());
        user.setUserImage(userDto.getUserImage());
        user.setUserPassword(userDto.getUserPassword());

        User updateUser = userRepo.save(user);
        UserDto userDto1 = entityToDto(updateUser);

        return userDto1;
    }

    @Override
    public void deleteUser(String userId) {

        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepo.delete(user);
    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<User> all = userRepo.findAll(pageable);

        PageableResponse<UserDto> response = Helper.getPageableResponse(all, UserDto.class);


        return response;
    }

    @Override
    public UserDto getUserById(String userId) {

        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        UserDto userDto = entityToDto(user);
        return userDto;
    }

    @Override
    public UserDto getUserByEmail(String userEmail) {
        User user = userRepo.findByUserEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return entityToDto(user);

    }

    @Override
    public List<UserDto> searchUser(String keyword) {

        List<User> users = userRepo.findByUserNameContaining(keyword);

        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }


    private UserDto entityToDto(User user) {
//
//        UserDto userDto= UserDto.builder()
//                                       .userId(savedUser.getUserId())
//                                       .name(savedUser.getName())
//                                       .password(savedUser.getPassword())
//                                       .email(savedUser.getEmail())
//                                       .about(savedUser.getAbout())
//                                       .imageName(savedUser.getImageName()).build();
//
        return mapper.map(user, UserDto.class);
    }


    private User dtoToEntity(UserDto userDto) {
//
//       User user= User.builder()
//                              .userId(userDto.getUserId())
//                              .name(userDto.getName())
//                              .email(userDto.getEmail())
//                              .about(userDto.getAbout())
//                             .password(userDto.getPassword())
//                             .imageName(userDto.getImageName()).build();
//
//
        return mapper.map(userDto, User.class);
    }
}
