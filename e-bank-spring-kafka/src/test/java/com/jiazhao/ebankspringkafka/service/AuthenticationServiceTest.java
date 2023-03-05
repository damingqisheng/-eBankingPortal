package com.jiazhao.ebankspringkafka.service;

import static org.mockito.Mockito.*;

import com.jiazhao.ebankspringkafka.Exception.UserNotExistException;
import com.jiazhao.ebankspringkafka.pojo.Token;
import com.jiazhao.ebankspringkafka.pojo.User;
import com.jiazhao.ebankspringkafka.util.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;

import javax.naming.AuthenticationException;

@SpringBootTest
public class AuthenticationServiceTest {

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAuthenticate_Success() throws UserNotExistException {
        // Arrange
        User user = new User("test", "123", 1);
        String token = "example-token";
        when(jwtUtil.generateToken(user.getUsername())).thenReturn(token);

        // Act
        AuthenticationService authenticationService = new AuthenticationService(authenticationManager, jwtUtil);
        Token result = authenticationService.authenticate(user);

        // Assert
        Assertions.assertEquals(token, result.getToken());
    }

    @Test
    public void testAuthenticate_UserNotExist() {
        // Arrange
        User user = new User("test", "123", 1);
        when(authenticationManager.authenticate(any())).thenThrow(AuthenticationException.class);

        // Act
        AuthenticationService authenticationService = new AuthenticationService(authenticationManager, jwtUtil);

        // Assert
        Assertions.assertThrows(UserNotExistException.class, () -> {
            authenticationService.authenticate(user);
        });
    }
}
