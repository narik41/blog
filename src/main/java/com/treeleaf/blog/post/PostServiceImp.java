package com.treeleaf.blog.post;

import com.treeleaf.blog.exception.InternalServerErrorException;
import com.treeleaf.blog.user.User;
import com.treeleaf.blog.user.UserRepostitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImp implements  PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepostitory userRepostitory;

    /**
     * Store the details of post
     *
     * @param request
     */
    public void store(PostRequest request){

        try{

            User user = userRepostitory.findById(1L).orElseThrow(()->new RuntimeException("user not found"));

            Post post = new Post();
            post.setTitle(request.getTitle());
            post.setDescription(request.getDescription());
            post.setUser(user);

            postRepository.save(post);
        }catch(Exception e){
            throw new InternalServerErrorException("Internal error. We are working actively to fix the error");
        }

    }
}
