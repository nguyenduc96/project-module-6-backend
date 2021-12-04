package com.diosa.model.user;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserRequest {
    private Long id;

    private String username;

    private String password;

    private String email;

    private MultipartFile avatar;
}
