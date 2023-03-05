package com.jiazhao.ebankspringkafka.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiazhao.ebankspringkafka.dao.UserDao;
import com.jiazhao.ebankspringkafka.pojo.User;
import com.jiazhao.ebankspringkafka.service.RegisterService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerIntegrationTest2 {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RegisterService registerService;

    @MockBean
    private UserDao userDao;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddHost() throws Exception {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        when(userDao.userExists(user)).thenReturn(false);

        mockMvc.perform(post("/register/host")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
        )
                .andExpect(status().isOk())
                .andReturn();

        verify(registerService, times(1)).add(user);
    }

    private static String asJsonString(final Object obj) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}
