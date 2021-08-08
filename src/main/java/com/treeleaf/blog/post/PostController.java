package com.treeleaf.blog.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("api/v1/")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<Map<String, Object>> index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) throws IOException {
        Map<String, Object> posts = postService.getList(page, size);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<PostView> view(@PathVariable("id") Long id){
        PostView post = postService.getPostDetails(id);
        return new ResponseEntity(post, HttpStatus.OK);
    }

    @PostMapping("post")
    public ResponseEntity<String> store(@Valid PostRequest request) throws IOException {
        postService.store(request);
        return new ResponseEntity("Post stored successfully.", HttpStatus.CREATED);
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @Valid PostRequest request){
        postService.update(id, request);
        return new ResponseEntity("Post updated successfully.", HttpStatus.CREATED);
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        postService.delete(id);
        return new ResponseEntity("Post deleted successfully.", HttpStatus.OK);
    }

}
