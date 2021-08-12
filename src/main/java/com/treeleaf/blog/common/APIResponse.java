package com.treeleaf.blog.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse {

    private Integer status;

    private String message;

    private Object data ;

    private Object error;

    private Object meta;
}
