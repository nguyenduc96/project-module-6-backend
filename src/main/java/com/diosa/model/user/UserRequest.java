package com.diosa.model.user;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class UserRequest {
    private Long id;

    @Size(min = 6, max = 18)
    private String username;

    @Size(min = 6, max = 18)
    private String password;

    @Email
    private String email;

    private MultipartFile avatar;
}
