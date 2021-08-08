package  com.treeleaf.blog.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/post/{id}/comment")
    public ResponseEntity<String> store(@PathVariable("id") Long postId, CommentRequest request){
        commentService.store(postId, request);

        return new ResponseEntity<>("Comment added to post successfully", HttpStatus.CREATED);
    }

    @PutMapping("/post/{postId}/comment/{id}")
    public ResponseEntity<String> update(
            @PathVariable("postId") Long postId,
            @PathVariable("id") Long commentId,
            CommentRequest request
    ){
        commentService.update(commentId, postId, request);

        return new ResponseEntity<>("Comment updated successfully.", HttpStatus.CREATED);
    }


}
