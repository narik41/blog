package com.treeleaf.blog.comment;

import com.treeleaf.blog.exception.InternalServerErrorException;
import com.treeleaf.blog.exception.ResourceNotFoundException;
import com.treeleaf.blog.exception.UserAuthorizationException;
import com.treeleaf.blog.post.Post;
import com.treeleaf.blog.post.PostRepository;
import com.treeleaf.blog.user.User;
import com.treeleaf.blog.user.UserRepostitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImp implements  CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepostitory userRepostitory;

    /**
     * Get by a comment by id and post id
     *
     * @param commentId
     * @param postId
     * @return
     */
    private Comment findByIdAndPostId(Long commentId, Long postId){
        return commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(
                        ()->new ResourceNotFoundException("Comment not found.")
                );
    }

    @Override
    public Map<String, Object> getList(Long postId) {

        Pageable paging = PageRequest.of(0,10);
        Page<CommentView> comments = commentRepository.findAllByPostId(postId, paging);
        Map<String, Object> response = new HashMap<>();
        response.put("comments", comments.getContent());
        response.put("currentPage", comments.getNumber());
        response.put("totalItems", comments.getTotalElements());
        response.put("totalPages", comments.getTotalPages());

        return response;
    }

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

    @Override
    public void update(Long commentId, Long postId, CommentRequest request) {
        Comment comment = findByIdAndPostId(commentId, postId);
        if(comment.getUser().getId() != 1){
            throw new UserAuthorizationException("You are not authorized to update the comment.");
        }

        try{
            comment.setComment(request.getComment());
            commentRepository.save(comment);
        }catch(Exception e){
            throw  new InternalServerErrorException("Internal error. We are working actively to fix the error");
        }
    }

    @Override
    public void delete(Long commentId, Long postId) {
        Comment comment = findByIdAndPostId(commentId, postId);
        if(comment.getUser().getId() != 1){
            throw new UserAuthorizationException("You are not authorized to update the comment.");
        }

        try{
            commentRepository.delete(comment);
        }catch(Exception e){
            throw  new InternalServerErrorException("Internal error. We are working actively to fix the error");
        }

    }
}
