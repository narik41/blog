package com.treeleaf.blog.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("profile")
    public ProfileView profile(){
        return userService.profile();
    }
}
