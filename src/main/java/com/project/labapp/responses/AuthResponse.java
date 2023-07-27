package com.project.labapp.responses;

import lombok.Data;

@Data
public class AuthResponse {

    String message;
    Boolean success;
    Long userId;
    Long roleId;
    String roleName;
}
