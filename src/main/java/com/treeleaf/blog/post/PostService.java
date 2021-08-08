package com.treeleaf.blog.post;

import java.util.Map;

public interface PostService {

    public Map<String, Object> getList();

    /**
     * Get the post details
     *
     * @param id
     * @return
     */
    public PostView getPostDetails(Long id);

    /**
     * Store the details of post
     *
     * @param request
     */
    public void store(PostRequest request);

    /**
     * Update the post details
     *
     * @param id
     * @param request
     */
    public void update(Long id, PostRequest request);

    /**
     * Remove the post details
     *
     * @param id
     */
    public void delete(Long id);
}
