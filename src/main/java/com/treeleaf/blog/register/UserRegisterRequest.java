package com.treeleaf.blog.register;

import com.treeleaf.blog.validator.password.PasswordConfirmation;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@PasswordConfirmation
public class UserRegisterRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email(message = "Email is not valid")
    private String email ;

    @NotBlank
    private String password ;

    private String passwordConfirmation ;
}
