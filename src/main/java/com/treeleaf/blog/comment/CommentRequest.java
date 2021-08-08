package  com.treeleaf.blog.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CommentRequest {

    @NotBlank(message = "This field is required.")
    @Size(min = 5,  message = "Value must be of 5 char length")
    private String comment;
}
