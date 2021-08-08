package  com.treeleaf.blog.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/post/{id}/comments")
    public ResponseEntity<Map<String, Object>> list(
            @PathVariable("id") Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ){
        Map<String, Object> comments = commentService.getList(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

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

        return new ResponseEntity<>("Comment updated successfully.", HttpStatus.OK);
    }

    @DeleteMapping("/post/{postId}/comment/{id}")
    public ResponseEntity<String> delete(@PathVariable("postId") Long postId, @PathVariable("id") Long commentId){
        commentService.delete( commentId, postId);

        return new ResponseEntity<>("Comment deleted successfully.", HttpStatus.OK);
    }
}
