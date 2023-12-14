package com.dattp.testbackenddev.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private int code;
    private Object data;
    private String message;
    public Response() {
        super();
    }
    @Builder
    public Response(int code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
}