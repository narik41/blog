package com.treeleaf.blog.comment;

import com.treeleaf.blog.user.User;
import org.springframework.beans.factory.annotation.Value;

public interface CommentView {

    String getComment();

    UserView getUser();

    interface UserView {

        Long getId();

        @Value("#{target.profile.firstName+' '+target.profile.lastName}")
        String getFullName();
    }

}
