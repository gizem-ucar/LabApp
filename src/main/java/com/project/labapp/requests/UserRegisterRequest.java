package com.project.labapp.requests;

import lombok.Data;

@Data
public class UserRegisterRequest {

    String userName;
    String password;
    String userFirstName;
    String userLastName;
    String userTC;
    String email;
    Long roleId;
}
