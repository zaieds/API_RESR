package com.user.register_user.controller;


import com.user.register_user.UserRestUser;
import com.user.register_user.models.DateHelpers;
import com.user.register_user.models.Gender;
import com.user.register_user.models.User;
import com.user.register_user.UserRestUser;
import com.user.register_user.service.UserService;
import static org.junit.jupiter.api.Assertions.*;


import com.user.register_user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.Mockito;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;

import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(UserController.class)

class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    Logger logger;

    @MockBean
    private UserService userService;

    private final Logger log = LoggerFactory.getLogger(UserControllerTest.class);


    @Test
    public void TestCreate() throws Exception {
        User savedUser = new User("user66",DateHelpers.parseDate("2000-01-30"),"France","0600800696",Gender.MALE);
        Mockito.when(logger.isInfoEnabled()).thenReturn(false);
        userService.create(savedUser);
        verify(userService, times(1)).create(savedUser);
        log.info("***** Test creat() OK! *****");
         }

    @Test
    public void TestFindAll() throws Exception{
        List<User> listUsers = new ArrayList<User>();
        listUsers.add(new User("user1", DateHelpers.parseDate("1967-09-09"),"France","0779800696", Gender.MALE));
        listUsers.add(new User("user2", DateHelpers.parseDate("1999-12-11"),"France","0656984596", Gender.FEMALE));
        listUsers.add(new User("user3", DateHelpers.parseDate("2000-01-30"),"France","0698700696", Gender.OTHER));
        listUsers.add(new User("user4", DateHelpers.parseDate("1977-08-23"),"France","0779971696", Gender.MALE));
        listUsers.add(new User("user",  DateHelpers.parseDate("1992-04-14"),"France","0779800000", Gender.FEMALE));
        listUsers.add(new User("user5", DateHelpers.parseDate("2001-11-02"),"France","0779800974", Gender.MALE));
        listUsers.add(new User("user6", DateHelpers.parseDate("1980-03-05"),"France","0600800696", Gender.OTHER));
        listUsers.add(new User("user7", DateHelpers.parseDate("1998-02-14"),"France"));
        listUsers.add(new User("user8", DateHelpers.parseDate("2003-09-19"),"France"));
        listUsers.add(new User("user9", DateHelpers.parseDate("1998-06-24"),"France"));

        when(userService.findAll()).thenReturn(listUsers);

        //test

        List<User> users = userService.findAll();
        assertNotNull(users);
        assertEquals(10,users.size());
        verify(userService, times(1)).findAll();
        log.info("***** Test TestFindAll() OK! *****");


    }

    @Test
   public void TestFindById() {
        when(userService.findById(1L)).thenReturn(new User("user1", DateHelpers.parseDate("1967-09-09"),"France","0779800696", Gender.MALE));
                User userFound = userService.findById(1L);
        assertEquals("user1",userFound.getUserName());
        assertEquals(DateHelpers.parseDate("1967-09-09"),userFound.getBirthday());
        assertEquals("France",userFound.getCountry());
        assertEquals("0779800696",userFound.getPhoneNumber());
        assertEquals(Gender.MALE,userFound.getGender());
        log.info("***** Test TestFindById() OK! *****");

    }

    @Test
   public void TestDelete() {
        User deleteUser = new User("user66",DateHelpers.parseDate("2000-01-30"),"France","0600800696",Gender.MALE);
        userService.delete(deleteUser.getId());
        verify(userService, times(1)).delete(deleteUser.getId());

        log.info("***** Test TestDelete() OK! *****");
    }



}