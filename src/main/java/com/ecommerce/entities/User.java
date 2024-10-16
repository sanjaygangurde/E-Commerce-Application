package com.ecommerce.entities;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {

    @Id
    private String userId;


    private String userName;


    private String userEmail;


    private String userPassword;


    private String userAbout;


    private String userImage;


}
