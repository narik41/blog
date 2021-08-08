package com.treeleaf.blog.comment;

public interface CommentService {

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
}
