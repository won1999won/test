package com.ohgiraffers.restapi.section03.valid;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String no) {
        super(no);
    }
}
