package com.treeleaf.blog.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("post")
    public ResponseEntity<String> store(@Valid PostRequest request){
        postService.store(request);

        return new ResponseEntity("Post stored successfully.", HttpStatus.CREATED);
    }

}
