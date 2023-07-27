package com.project.labapp.requests;

import lombok.Data;

@Data
public class UserLoginRequest {
    String userName;
    String password;

    public UserLoginRequest(){
        super();
    }
}
