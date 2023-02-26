package com.jiazhao.ebankspringkafka.service;

/**
 * @author Lee
 * @date 12/17/2021 - 8:55 PM
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiazhao.ebankspringkafka.Exception.UserAlreadyExistException;
import com.jiazhao.ebankspringkafka.dao.UserDao;
import com.jiazhao.ebankspringkafka.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {
    private UserDao userRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterService(UserDao userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(User user) throws UserAlreadyExistException, JsonProcessingException {
        if(userRepo.userExists(user)) {
            throw new UserAlreadyExistException("User already exists");
        }

        user.setPassword(user.getPassword());
        user.setCreditNum(5000);
        user.setDebitNum(5000);
        userRepo.saveUser(user);
    }

}
