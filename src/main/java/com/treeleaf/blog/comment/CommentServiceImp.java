package com.treeleaf.blog.comment;

import com.treeleaf.blog.exception.InternalServerErrorException;
import com.treeleaf.blog.exception.ResourceNotFoundException;
import com.treeleaf.blog.post.Post;
import com.treeleaf.blog.post.PostRepository;
import com.treeleaf.blog.user.User;
import com.treeleaf.blog.user.UserRepostitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImp implements  CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepostitory userRepostitory;

    @Override
    public void store(Long postId, CommentRequest request){
        User user = userRepostitory.findById(1L).orElseThrow(()->new ResourceNotFoundException("user not found"));
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post not found"));

        try{

            Comment comment = new Comment() ;
            comment.setComment(request.getComment());
            comment.setPost(post);
            comment.setUser(user);

            commentRepository.save(comment);
        }catch(Exception e){
            throw  new InternalServerErrorException("Internal error. We are working actively to fix the error");
        }

    }

}
