package com.treeleaf.blog.post;

import org.springframework.beans.factory.annotation.Value;

public interface OnlyPost {

    Long getId();

    String getTitle();

    String getDescription();

    UserView getUser();

    interface UserView {

        Long getId();

        @Value("#{target.profile.firstName+' '+target.profile.lastName}")
        String getFullName();
    }
}
