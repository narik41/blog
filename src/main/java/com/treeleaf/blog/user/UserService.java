package com.treeleaf.blog.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

public interface UserService {

    /**
     * Get the user profile details
     *
     * @return
     */
    public ProfileView profile();
}
