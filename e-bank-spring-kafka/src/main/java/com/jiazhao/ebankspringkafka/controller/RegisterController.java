package com.jiazhao.ebankspringkafka.controller;

/**
 * @author Lee
 * @date 12/18/2021 - 10:26 AM
 */
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiazhao.ebankspringkafka.pojo.User;
import com.jiazhao.ebankspringkafka.service.RegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    private RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }


    @PostMapping("/register/host")
    public int addHost(@RequestBody User user) throws JsonProcessingException {
        registerService.add(user);
        return 200;
    }
}
