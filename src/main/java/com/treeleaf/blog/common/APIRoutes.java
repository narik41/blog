package com.treeleaf.blog.common;

public final class APIRoutes {

    private APIRoutes(){}

    public static final String LOGIN = "/api/v1/login";
    public static final String REGISTER  = "/api/v1/register";
    public static final String IMAGES = "images/**";

    public class ROLE {
        public static final String GET_ROLES = "api/v1/roles";
        public static final String STORE_ROLE = "api/v1/role";
        public static final String SINGLE_ROLE = "api/v1/role/{id}";
    }

    public class COMMENT{
        public static final String GET_COMMENTS = "api/v1/post/{id}/comments";
        public static final String STORE_COMMENTS = "api/v1/post/{id}/comment";
        public static final String SINGLE_COMMENTS = "api/v1/post/{postId}/comment/{id}";
    }

    public class POST {
        public static final String GET_POSTS = "api/v1/posts";
        public static final String STORE_POST = "api/v1/post";
        public static final String SINGLE_POST = "api/v1/post/{id}";
    }
}
