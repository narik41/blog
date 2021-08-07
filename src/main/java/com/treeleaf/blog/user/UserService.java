package com.treeleaf.blog.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

public interface UserService {

    public UserDetails loadUserByUsername(String username);
}
