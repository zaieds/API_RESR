package com.user.register_user;

import com.fasterxml.jackson.databind.JsonNode;
import com.user.register_user.models.DateHelpers;
import com.user.register_user.models.Gender;
import com.user.register_user.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRestUserTest {
    // Test users
    //id = 1
    private User user1 = new User("user1", DateHelpers.parseDate("1992-07-09 02:00:00 CEST"), "France", "0766600004", Gender.FEMALE);

    //id = 2
    private User user2 = new User("user2", DateHelpers.parseDate("1980-01-10 02:00:00 CEST"), "France", "0766648004", Gender.MALE);

    //id = 5
    private User user3 = new User("user5", DateHelpers.parseDate("1986-01-15 02:00:00 CEST"), "France");

    @LocalServerPort
    private int port;
    @Autowired
    private RestTemplate restTemplate;

    private UserRestUser userRest;

    @BeforeEach
    void setup() {
        //assertNotNull(restTemplate);
        userRest = new UserRestUser(restTemplate, "http://localhost", port);
    }


    @Test
    void test_getForEntity() {
        ResponseEntity<User> entity = userRest.getForEntity(1);

        assertNotNull(entity);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertNotNull(entity.getBody());
        assertEquals(user1, entity.getBody());
    }

    @Test
    void test_getForEntityNotFound() {
        ResponseEntity<User> entity = userRest.getForEntity(-1);

        assertNotNull(entity);
        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
        assertNull(entity.getBody());
    }

    @Test
    void test_getAllUsers() {
        List<User> users = userRest.getAllUsers(1, 15);

        assertNotNull(userRest);
        assertEquals(15, users.size());
        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user3);
        assertEquals(user1, users.get(0));
        assertEquals(user2, users.get(1));
        assertEquals(user3, users.get(4));
    }


    @Test
    void test_getForObject() {
        Optional<User> user = userRest.getForObject(1);

        assertNotNull(user);
        assertTrue(user.isPresent());
        assertEquals(user1, user);
    }

    @Test
    void getAsJsonNode() throws Exception {
        JsonNode jsonNode = userRest.getAsJsonNode(4);

        assertNotNull(jsonNode);
        assertEquals(user3.getId(), jsonNode.path("id").asLong());
        assertEquals(user3.getUserName(), jsonNode.path("userName").asText());
        assertEquals(user3.getBirthday(), jsonNode.path("birthday").asText(DateFormat.getDateInstance().format("yyyy-MM-dd")));
        assertEquals(user3.getCountry(), jsonNode.path("country").asText());
    }

    @Test
    void test_postForObject() {
        User userToCreate = new User();
        userToCreate.setUserName("user-test");
        userToCreate.setBirthday(DateHelpers.parseDate("1998-06-24"));
        userToCreate.setCountry("France");
        userToCreate.setPhoneNumber("0756948231");
        userToCreate.setGender(Gender.MALE);

        User createdUser = userRest.postForObject(userToCreate);

        assertNotNull(createdUser);
        assertTrue(createdUser.getId() != 0);
        assertEquals("user-test", createdUser.getUserName());
        assertEquals(DateHelpers.parseDate("1998-06-24"), createdUser.getBirthday());
        assertEquals("France", createdUser.getCountry());
        assertEquals("0756948231", createdUser.getPhoneNumber());
        assertEquals(Gender.MALE, createdUser.getGender());
    }

    @Test
    void test_postForLocation() {
        User userToCreate = new User();
        userToCreate.setUserName("user-test");
        userToCreate.setBirthday(DateHelpers.parseDate("1998-06-24"));
        userToCreate.setCountry("France");
        userToCreate.setPhoneNumber("0756948231");
        userToCreate.setGender(Gender.MALE);

        URI location = userRest.postForLocation(userToCreate);

        assertNotNull(location);
    }

    @Test
    void test_postForEntity() {
        User userToCreate = new User();
        userToCreate.setUserName("user-test");
        userToCreate.setBirthday(DateHelpers.parseDate("1998-06-24"));
        userToCreate.setCountry("France");
        userToCreate.setPhoneNumber("0756948231");
        userToCreate.setGender(Gender.MALE);

        ResponseEntity<User> entity = userRest.postForEntity(userToCreate);

        assertNotNull(entity);
        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
        assertNotNull(entity.getBody());
        assertTrue(entity.getBody().getId() != 0);
        assertEquals("user-test", entity.getBody().getUserName());
        assertEquals(DateHelpers.parseDate("1998-06-24"), entity.getBody().getBirthday());
        assertEquals("France", entity.getBody().getCountry());
        assertEquals("0756948231", entity.getBody().getPhoneNumber());
        assertEquals(Gender.MALE, entity.getBody().getGender());

    }

    @Test
    void put() {

        @SuppressWarnings("OptionalGetWithoutIsPresent")
        User user = userRest.getForObject(15).get();
        user.setUserName("user2");
        user.setBirthday(DateHelpers.parseDate("1993-07-23"));
        user.setCountry("France");
        user.setPhoneNumber("0600098998");
        user.setGender(Gender.MALE);

        userRest.put(user);

        @SuppressWarnings("OptionalGetWithoutIsPresent")
        User updatedUser = userRest.getForObject(15).get();
        assertNotNull(updatedUser);
        assertEquals(15, updatedUser.getId());
        assertEquals("user2", updatedUser.getUserName());
        assertEquals(DateHelpers.parseDate("1993-07-23"), updatedUser.getBirthday());
        assertEquals("France", updatedUser.getCountry());
        assertEquals("0600098998", updatedUser.getPhoneNumber());
        assertEquals(Gender.MALE, updatedUser.getGender());
    }

    @Test
    void test_putWithExchange() {
        @SuppressWarnings("OptionalGetWithoutIsPresent")

        User user = userRest.getForObject(2).get();
        user.setUserName("user2");
        user.setBirthday(DateHelpers.parseDate("1980-01-10"));
        user.setCountry("France");
        user.setPhoneNumber("0766648004");
        user.setGender(Gender.MALE);

        ResponseEntity<User> entity = userRest.putWithExchange(user);

        assertNotNull(entity);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertNotNull(entity.getBody());
        assertEquals(2, entity.getBody().getId());
        assertEquals("user2", entity.getBody().getUserName());
        assertEquals(DateHelpers.parseDate("1980-01-10"), entity.getBody().getBirthday());
        assertEquals("France", entity.getBody().getCountry());
        assertEquals("0766648004", entity.getBody().getPhoneNumber());
        assertEquals(Gender.FEMALE, entity.getBody().getGender());
    }

    @Test
    void delete() {
        userRest.delete(6);

        assertTrue(userRest.getForObject(6).isEmpty());
    }

    @Test
    void deleteWithExchange() {
        ResponseEntity<Void> entity = userRest.deleteWithExchange(99);

        assertNotNull(entity);
        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
    }

    @Test
    void headForHeaders() {
        HttpHeaders httpHeaders = userRest.headForHeaders();

        assertNotNull(httpHeaders.getContentType());
        assertTrue(httpHeaders.getContentType().includes(MediaType.APPLICATION_JSON));
    }

    @Test
    void optionsForAllow() {
        Set<HttpMethod> httpMethods = userRest.optionsForAllow(9);
        List<HttpMethod> expectedHttpMethods = List.of(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE);

        assertTrue(httpMethods.containsAll(expectedHttpMethods));
    }

}