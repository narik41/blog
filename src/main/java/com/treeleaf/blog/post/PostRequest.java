package com.treeleaf.blog.post;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PostRequest {

    @NotBlank(message = "Title is required.")
    @Size(min = 5,  message = "Title must be of 5 char length")
    private String title ;

    @NotBlank(message = "Description is required.")
    private String description ;

}
