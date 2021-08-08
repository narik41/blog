package com.treeleaf.blog.post;

public interface PostService {

    /**
     * Store the details of post
     *
     * @param request
     */
    public void store(PostRequest request);
}
