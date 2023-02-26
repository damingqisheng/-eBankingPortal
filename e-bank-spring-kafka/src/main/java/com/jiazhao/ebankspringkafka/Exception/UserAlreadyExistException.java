package com.jiazhao.ebankspringkafka.Exception;

/**
 * @author Lee
 * @date 12/18/2021 - 10:24 AM
 */


public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
