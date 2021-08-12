package com.treeleaf.blog.common;

public final class APIRoutes {

    private APIRoutes(){}

    public class ROLE {
        public static final String GET_ROLES = "roles";
        public static final String STORE_ROLE = "role";
        public static final String SINGLE_ROLE = "/role/{id}";
    }

    public class COMMENT{
        public static final String GET_COMMENTS = "post/{id}/comments";
        public static final String STORE_COMMENTS = "/post/{id}/comment";
        public static final String SINGLE_COMMENTS = "/post/{postId}/comment/{id}";
    }

    public class POST {
        public static final String GET_POSTS = "posts";
        public static final String STORE_POST = "post";
        public static final String SINGLE_POST = "post/{id}";
    }
}
