package com.treeleaf.blog.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/")
public class UserRegisterController {

    @Autowired
    UserRegisterSerivce userRegisterSerivce ;

    @PostMapping("register")
    public ResponseEntity<String> register(@Valid UserRegisterRequest request){
        userRegisterSerivce.register(request);

        return new ResponseEntity<>("User is registered", HttpStatus.CREATED);
    }
}
