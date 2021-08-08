package com.treeleaf.blog.post;

import com.treeleaf.blog.comment.CommentView;
import com.treeleaf.blog.exception.UserAuthorizationException;
import com.treeleaf.blog.exception.InternalServerErrorException;
import com.treeleaf.blog.exception.ResourceNotFoundException;
import com.treeleaf.blog.user.User;
import com.treeleaf.blog.user.UserRepostitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostServiceImp implements  PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepostitory userRepostitory;

    public Map<String, Object> getList(){
        Pageable paging = PageRequest.of(0,10, Sort.by("id").descending());
        Page<OnlyPost> posts = postRepository.findAllProjectBy(paging);

        Map<String, Object> response = new HashMap<>();
        response.put("posts", posts.getContent());
        response.put("currentPage", posts.getNumber());
        response.put("totalItems", posts.getTotalElements());
        response.put("totalPages", posts.getTotalPages());

        return response;
    }

    @Override
    public PostView getPostDetails(Long id) {

        List<PostView> posts = postRepository.findAllProjectById(id);
        return posts.stream().findFirst().get();

    }

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
