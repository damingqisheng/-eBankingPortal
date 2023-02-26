package com.jiazhao.ebankspringkafka.Exception;

/**
 * @author Lee
 * @date 12/22/2021 - 5:45 PM
 */
public class UserNotExistException extends RuntimeException{
    public UserNotExistException(String message) {
        super(message);
    }
}
