package com.ecommerce.dtos;


import com.ecommerce.validate.ImageNameValid;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDto {

    private String userId;

    @Size(min = 3, max = 20, message = "Invalid name")
    private String userName;

   // @Email(message = "Invalid email")
    @Pattern(regexp ="^[a-z0-9][a-zA-Z0-9.-]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message = "Invalid email")
    @NotBlank(message = "Email must not be empty")
    private String userEmail;

    @NotBlank(message = "Password is required")
    private String userPassword;

    @NotBlank(message = "Write something about yourself..")
    private String userAbout;

    @ImageNameValid
    private String userImage;

}
