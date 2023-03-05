package com.jiazhao.ebankspringkafka.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jiazhao.ebankspringkafka.dao.UserDao;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class MyUserDetailsServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private MyUserDetailsService myUserDetailsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadUserByUsernameSuccess() {
        String username = "testuser";
        String password = "testpassword";
        String currencyType = "ROLE_USER";
        com.jiazhao.ebankspringkafka.pojo.User user = new com.jiazhao.ebankspringkafka.pojo.User();
        user.setUsername(username);
        user.setPassword(password);
        user.setCurrencyType(currencyType);

        when(userDao.findByUsername(username)).thenReturn(user);

        User expectedUser = new User(username, new BCryptPasswordEncoder().encode(password),
                AuthorityUtils.commaSeparatedStringToAuthorityList(currencyType));

        User actualUser = (User) myUserDetailsService.loadUserByUsername(username);

        Assertions.assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        Assertions.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        Assertions.assertEquals(expectedUser.getAuthorities(), actualUser.getAuthorities());
    }

    @Test
    public void testLoadUserByUsernameFail() {
        String username = "testuser";

        when(userDao.findByUsername(username)).thenReturn(null);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            myUserDetailsService.loadUserByUsername(username);
        });
    }
}
