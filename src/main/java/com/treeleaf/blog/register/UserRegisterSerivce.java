package com.treeleaf.blog.register;

import com.treeleaf.blog.exception.UserRegisterStoredException;

public interface UserRegisterSerivce {

    /**
     * Register the new user in the system
     *
     * @param request
     */
    public void register(UserRegisterRequest request) throws UserRegisterStoredException;
}
