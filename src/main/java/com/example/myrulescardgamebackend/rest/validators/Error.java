package com.example.myrulescardgamebackend.rest.validators;

public class Error extends Result {
    public Error(String msg) {
        super(false, msg);
    }
}
