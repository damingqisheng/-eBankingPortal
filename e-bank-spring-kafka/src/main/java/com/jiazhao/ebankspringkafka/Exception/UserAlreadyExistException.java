package com.jiazhao.ebankspringkafka.Exception;



public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
