package com.treeleaf.blog.util;

import com.treeleaf.blog.user.User;
import com.treeleaf.blog.user.UserRepostitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LoggedinUser {

    @Autowired
    UserRepostitory userRepostitory;

    /**
     * Get the currenlty logged in user name
     * @return
     */
    public String username(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public User details(){
        String email = this.username();
        return  userRepostitory.findByEmail(email);
    }
}
