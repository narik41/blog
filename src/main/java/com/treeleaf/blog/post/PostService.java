package com.treeleaf.blog.post;

public interface PostService {

    /**
     * Store the details of post
     *
     * @param request
     */
    public void store(PostRequest request);

    /**
     * Remove the post details
     *
     * @param id
     */
    public void delete(Long id);
}
