package com.recipee.rest;

import lombok.Data;

import java.util.Date;
@Data
public class Response<T> {
    private Long timestamp = new Date().getTime();
    private String message;
    private String code = "S-APP-1";
    private T data;

    public Response(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public Response(String message, T data, String code) {
        this.message = message;
        this.data = data;
        this.code = code;
    }
}
