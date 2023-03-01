package com.jiazhao.ebankspringkafka.Exception;

public class UserNotExistException extends RuntimeException{
    public UserNotExistException(String message) {
        super(message);
    }
}
