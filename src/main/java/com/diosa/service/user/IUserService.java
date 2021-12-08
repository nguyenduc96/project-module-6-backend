package com.diosa.service.user;

import com.diosa.model.user.User;
import com.diosa.service.IBaseService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface IUserService extends IBaseService<User>, UserDetailsService {
    Optional<User> findByUsername(String username);

    Iterable<User> findUsersByProjectId(Long projectId);}
