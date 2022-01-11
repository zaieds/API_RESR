package com.user.register_user;

import com.user.register_user.models.DateHelpers;
import com.user.register_user.models.Gender;
import com.user.register_user.models.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest(classes = RegisterUserApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    public class IntegrationTests {
        @LocalServerPort
        private int port;

        @Autowired
        private TestRestTemplate restTemplate;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

        @Test
        public void testGetAllUsers() {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<String>(null, headers);
            ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/users",
                    HttpMethod.GET, entity, String.class);
            assertNotNull(response.getBody());
        }

        @Test
        public void testGetEmployeeById() {
        int id = 1;
         User user = restTemplate.getForObject(getRootUrl() + "/users/1", User.class);
            System.out.println(user.getUserName());
            assertNotNull(user);
        }

        @Test
        public void testCreateEmployee() throws URISyntaxException {

            final String baseUrl = getRootUrl() + "/users/";
            URI uri = new URI(baseUrl);

            User user = new User("user66",DateHelpers.parseDate("2000-01-30"),"France","0600800696",Gender.MALE);

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-COM-PERSIST", "true");

            HttpEntity<User> request = new HttpEntity<>(user, headers);

            ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

            //Verify request succeed
            Assert.assertEquals(201, result.getStatusCodeValue());;
        }

    @Test
        public void testUpdateEmployee() {
            int id = 1;
            User user = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
            user.setUserName("user2");
            user.setBirthday(DateHelpers.parseDate("1992-07-09"));
            user.setCountry("France");
            user.setPhoneNumber("0769843211");
            user.setGender(Gender.MALE);

            restTemplate.put(getRootUrl() + "/users/" + id, user);
            User updatedUser = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
            assertNotNull(updatedUser);
        }

        @Test
        public void testDeleteEmployee() {
            int id = 2;
            User user = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
            assertNotNull(user);
            restTemplate.delete(getRootUrl() + "/users/" + id);
            try {
                user = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
            } catch (final HttpClientErrorException e) {
                assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
            }
        }


    }