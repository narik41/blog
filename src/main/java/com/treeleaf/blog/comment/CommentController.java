package  com.treeleaf.blog.comment;

import com.treeleaf.blog.common.APIResponse;
import com.treeleaf.blog.common.APIRoutes;
import com.treeleaf.blog.util.Meta;
import com.treeleaf.blog.util.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    Translator translator;

    @GetMapping(APIRoutes.COMMENT.GET_COMMENTS)
    public ResponseEntity<APIResponse> list(
            @PathVariable("id") Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ){
        Page<CommentView> comments = commentService.getList(postId, page, size);

        Meta meta = new Meta();
        meta.setCurrentPage(comments.getNumber());
        meta.setTotalItems(comments.getTotalElements());
        meta.setTotalPages(comments.getTotalPages());

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(comments.getContent());
        apiResponse.setMessage(translator.toLocale("success_comment_list"));
        apiResponse.setMeta(meta);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping(APIRoutes.COMMENT.STORE_COMMENTS)
    public ResponseEntity<APIResponse> store(@PathVariable("id") Long postId, CommentRequest request){
        commentService.store(postId, request);

        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(translator.toLocale("success_comment_added"));
        apiResponse.setStatus(HttpStatus.OK.value());

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PutMapping(APIRoutes.COMMENT.SINGLE_COMMENTS)
    public ResponseEntity<APIResponse> update(
            @PathVariable("postId") Long postId,
            @PathVariable("id") Long commentId,
            CommentRequest request
    ){
        commentService.update(commentId, postId, request);

        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(translator.toLocale("success_comment_updated"));
        apiResponse.setStatus(HttpStatus.OK.value());

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping(APIRoutes.COMMENT.SINGLE_COMMENTS)
    public ResponseEntity<APIResponse> delete(@PathVariable("postId") Long postId, @PathVariable("id") Long commentId){
        commentService.delete( commentId, postId);

        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(translator.toLocale("success_comment_deleted"));
        apiResponse.setStatus(HttpStatus.OK.value());

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
