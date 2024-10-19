package com.ecommerce.controller;

import com.ecommerce.dtos.CategoryDto;
import com.ecommerce.dtos.UserDto;
import com.ecommerce.payloads.ImageResponse;
import com.ecommerce.service.CategoryI;
import com.ecommerce.service.FileServiceI;
import com.ecommerce.service.UserServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/files")
public class FileController {

    private Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileServiceI fileServiceI;

    @Autowired
    private UserServiceI userServiceI;

    @Autowired
    private CategoryI categoryI;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;




    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadFile(@RequestParam("userImage") MultipartFile image, @PathVariable String userId) throws IOException {

        String imageName = fileServiceI.uploadFile(image, imageUploadPath);

        UserDto userById = userServiceI.getUserById(userId);
        userById.setUserImage(imageName);

        UserDto userDto = userServiceI.updateUser(userById, userId);

        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();

        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }


    @GetMapping("/getImage/{userId}")
    public void getUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {

        UserDto user = userServiceI.getUserById(userId);

        logger.info("User image name : {} ", user.getUserName());

        InputStream file = fileServiceI.getFile(imageUploadPath, user.getUserImage());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(file, response.getOutputStream());

    }


}
