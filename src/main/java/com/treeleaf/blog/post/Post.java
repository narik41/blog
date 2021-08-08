package com.treeleaf.blog.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.treeleaf.blog.comment.Comment;
import com.treeleaf.blog.user.User;
import lombok.Data;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity()
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String title;

    private String description;

    @Column(name="created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt ;

    @Column(name="updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt ;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Comment> comments;
}
