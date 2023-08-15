package com.project.labapp.requests;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserUpdateRequest {

    String userName;
    String password;
    String userFirstName;
    String userLastName;
    MultipartFile userImageFile;
    String userTC;
    String email;
    Long roleId;
    String roleName;

}
