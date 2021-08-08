package com.treeleaf.blog.post;

import com.treeleaf.blog.comment.CommentView;
import com.treeleaf.blog.user.User;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Set;

public interface PostView {

    Long getId();

    String getTitle();

    String getDescription();

    List<CommentView> getComments();

    UserView getUser();

    interface UserView {

        Long getId();

        @Value("#{target.profile.firstName+' '+target.profile.lastName}")
        String getFullName();
    }
}
