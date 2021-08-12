package com.treeleaf.blog.comment;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface CommentService {

    /**
     * Get a list of comment of a post
     *
     * @param postId
     * @return
     */
    public Page<CommentView> getList(Long postId, int pageNumber, int size);

    /**
     * Check if post exists and store the comment details
     *
     * @param postId
     * @param request
     */
    public void store(Long postId, CommentRequest request);

    /**
     * Update the comment details
     *
     * @param commentId
     * @param request
     */
    public void update(Long commentId, Long postId, CommentRequest request);

    /**
     * Remove the comment details from the record
     *
     * @param commentId
     */
    public void delete(Long commentId, Long postId);
}
