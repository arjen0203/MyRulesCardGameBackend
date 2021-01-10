package com.example.myrulescardgamebackend.rest.validators;

public class Result {

    private boolean success;
    private String message;

    public Result(boolean success, String msg) {
        this.success = success;
        this.message = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
