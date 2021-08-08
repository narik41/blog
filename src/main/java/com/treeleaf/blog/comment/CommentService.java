package com.treeleaf.blog.comment;

import java.util.List;
import java.util.Map;

public interface CommentService {

    /**
     * Get a list of comment of a post
     *
     * @param postId
     * @return
     */
    public Map<String, Object> getList(Long postId);

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
