package com.treeleaf.blog.post;

import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface OnlyPost {

    Long getId();

    String getTitle();

    String getDescription();

    UserView getUser();

    Set<PostView.ImageView> getImages();

    interface ImageView{
        Long getId();

        @Value("#{target.getUrl()}")
        String getUrl();
    }

    interface UserView {

        Long getId();

        @Value("#{target.profile.firstName+' '+target.profile.lastName}")
        String getFullName();
    }
}
