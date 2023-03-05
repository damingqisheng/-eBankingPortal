package com.jiazhao.ebankspringkafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiazhao.ebankspringkafka.Exception.UserAlreadyExistException;
import com.jiazhao.ebankspringkafka.dao.UserDao;
import com.jiazhao.ebankspringkafka.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegisterServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterService registerService;

    @Test
    public void testAddUser() throws UserAlreadyExistException, JsonProcessingException {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setUserId(1);

        when(userDao.userExists(user)).thenReturn(false);

        registerService.add(user);

        verify(userDao, times(1)).saveUser(user);
    }

    @Test(expected = UserAlreadyExistException.class)
    public void testAddExistingUser() throws UserAlreadyExistException, JsonProcessingException {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        when(userDao.userExists(user)).thenReturn(true);

        registerService.add(user);
    }
}
