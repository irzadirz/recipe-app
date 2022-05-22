package com.recipee.rest;

public interface ResponseBuilder {
    default  <Data> Response<Data> buildResponse(String message, Data data) {
        return new Response<>(message, data);
    }

    default  <Data> Response<Data> buildResponse(String message, Data data, String code) {
        return new Response<>(message, data, code);
    }

    default Response buildResponse(String message) {
        return buildResponse(message, null);
    }
}
