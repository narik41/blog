package com.treeleaf.blog.post;

import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.Map;

public interface PostService {

    public Page<OnlyPost> getList(int page, int size);

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
    public void store(PostRequest request) throws IOException;

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
