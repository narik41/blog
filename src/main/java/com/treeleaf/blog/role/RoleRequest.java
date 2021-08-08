package com.treeleaf.blog.role;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RoleRequest {

    @NotBlank(message = "Name is required.")
    @Size(min = 2,  message = "Name must be of 5 char length")
    private String name ;
}
