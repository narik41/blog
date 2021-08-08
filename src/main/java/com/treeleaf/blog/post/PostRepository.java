package com.treeleaf.blog.post;

import com.treeleaf.blog.comment.CommentView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository  extends CrudRepository<Post, Long> {

    @Query(nativeQuery = true, value = "SELECT p.* FROM posts p WHERE id=:id")
    PostView findDetailsById(Long id);

    List<PostView> findAllProjectById(Long id);

    Page<OnlyPost> findAllProjectBy(Pageable paging);

}
