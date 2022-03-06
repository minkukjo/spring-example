package com.example.example.presentation.api.response;

public class OkResponse {
    private final int code;
    private final String message;

    public OkResponse() {
        code = 200;
        message = "OK";
    }

    protected OkResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static OkResponse ok() {
        return new OkResponse(200, "OK");
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
