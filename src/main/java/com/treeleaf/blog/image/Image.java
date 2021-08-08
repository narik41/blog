package com.treeleaf.blog.image;

import com.treeleaf.blog.post.Post;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity()
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String name;

    private String path;

    @Column(name="created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt ;

    @Column(name="updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt ;

    @ManyToMany(mappedBy = "images")
    private Set<Post> posts;

    public String getUrl(){
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("images/"+this.name)
                .toUriString();
    }

    public String getDirPath(){
        return  this.path.substring("file:/".length());
    }
}