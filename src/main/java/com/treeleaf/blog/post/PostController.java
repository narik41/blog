package com.treeleaf.blog.post;

import com.treeleaf.blog.common.APIResponse;
import com.treeleaf.blog.common.APIRoutes;
import com.treeleaf.blog.util.Meta;
import com.treeleaf.blog.util.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.io.IOException;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    Translator translator;

    @GetMapping(APIRoutes.POST.GET_POSTS)
    public ResponseEntity<APIResponse> index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ){
        Page<OnlyPost> posts = postService.getList(page, size);

        Meta meta = new Meta();
        meta.setCurrentPage(posts.getNumber());
        meta.setTotalItems(posts.getTotalElements());
        meta.setTotalPages(posts.getTotalPages());

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(posts.getContent());
        apiResponse.setMessage(translator.toLocale("success_post_list"));
        apiResponse.setMeta(meta);

        return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
    }

    @GetMapping(APIRoutes.POST.SINGLE_POST)
    public ResponseEntity<APIResponse> view(@PathVariable("id") Long id){
        PostView post = postService.getPostDetails(id);

        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(translator.toLocale("success_post_detail"));
        apiResponse.setData(post);
        apiResponse.setStatus(HttpStatus.OK.value());

        return new ResponseEntity(apiResponse, HttpStatus.OK);
    }

    @PostMapping(APIRoutes.POST.STORE_POST)
    public ResponseEntity<APIResponse> store(@Valid PostRequest request) throws IOException {
        postService.store(request);

        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(translator.toLocale("success_post_created"));
        apiResponse.setStatus(HttpStatus.CREATED.value());

        return new ResponseEntity(apiResponse, HttpStatus.CREATED);
    }

    @PutMapping(APIRoutes.POST.SINGLE_POST)
    public ResponseEntity<APIResponse> update(@PathVariable("id") Long id, @Valid PostRequest request){
        postService.update(id, request);

        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(translator.toLocale("success_post_updated"));
        apiResponse.setStatus(HttpStatus.CREATED.value());

        return new ResponseEntity(apiResponse, HttpStatus.CREATED);
    }

    @DeleteMapping(APIRoutes.POST.SINGLE_POST)
    public ResponseEntity<APIResponse> delete(@PathVariable("id") Long id){
        postService.delete(id);

        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(translator.toLocale("success_post_deleted"));
        apiResponse.setStatus(HttpStatus.OK.value());

        return new ResponseEntity(apiResponse, HttpStatus.OK);
    }
}
