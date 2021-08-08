package com.treeleaf.blog.post;

import com.treeleaf.blog.exception.UserAuthorizationException;
import com.treeleaf.blog.exception.InternalServerErrorException;
import com.treeleaf.blog.exception.ResourceNotFoundException;
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

    @Override
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

    @Override
    public void update(Long id, PostRequest request) {

        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post not found."));
        if(post.getUser().getId() != 1){
            throw new UserAuthorizationException("You are not authorized to delete the post.");
        }

        try{
            post.setTitle(request.getTitle());
            post.setDescription(request.getDescription());

            postRepository.save(post);
        }catch(Exception e){
            throw new InternalServerErrorException("Internal error. We are working actively to fix the error");
        }
    }

    @Override
    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post not found."));
        if(post.getUser().getId() != 1){
            throw new UserAuthorizationException("You are not authorized to delete the post.");
        }

        postRepository.delete(post);
    }


}
