package  com.treeleaf.blog.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.treeleaf.blog.post.Post;
import com.treeleaf.blog.user.User;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity()
@Data
@Table(name = "post_comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String comment;

    @Column(name="created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt ;

    @Column(name="updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt ;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;
}
