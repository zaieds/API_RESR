package com.user.register_user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.util.*;

import com.user.register_user.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings("WeakerAccess")
public class UserRestUser {


        private static final String RESOURCE_PATH = "/users";

        private Logger LOG = LoggerFactory.getLogger(UserRestUser.class);
        private String REQUEST_URI;
        private RestTemplate restTemplate;

        public UserRestUser(RestTemplate restTemplate, String host, int port) {
            this.restTemplate = restTemplate;
            this.REQUEST_URI = host + ":" + port + RESOURCE_PATH;
        }

        /**
         * Requests the user resource for the specified id via HTTP GET using RestTemplate method getForEntity.
         * @param id the id of the employee resource
         * @return a ResponseEntity that wraps http status code, http headers and the body of type {@link User}
         */
    public ResponseEntity<User> getForEntity(long id) {
        ResponseEntity<User> entity = restTemplate.getForEntity(REQUEST_URI + "/{id}",
                User.class,
                Long.toString(id));
                return entity;
    }

        /**
         * Requests a specified amount of employee resources via HTTP GET using RestTemplate method getForEntity.
         * The amount is specified by the given page and pageSize parameter.
         * @param page the page
         * @param pageSize the amount of elements per page
         * @return a list of employees
         */
        public List<User> getAllUsers(int page, int pageSize) {
            String requestUri = REQUEST_URI + "?page={page}&pageSize={pageSize}";

            Map<String, String> urlParameters = new HashMap<>();
            urlParameters.put("page", Integer.toString(page));
            urlParameters.put("pageSize", Long.toString(pageSize));

            ResponseEntity<User[]> entity = restTemplate.getForEntity(requestUri,
                    User[].class,
                    urlParameters);

            return entity.getBody() != null? Arrays.asList(entity.getBody()) : Collections.emptyList();
        }

        /**
         * Requests the user resource for the specified id via HTTP GET using RestTemplate method getForObject.
         * @param id the id of the user resource
         * @return the employee as {@link Optional} or an empty {@link Optional} if resource not found.
         */
        public Optional<User> getForObject(long id) {
            User employee = restTemplate.getForObject(REQUEST_URI + "/{id}",
                    User.class,
                    Long.toString(id));

            return Optional.ofNullable(employee);
        }

        /**
         * Requests the user resource for the specified id via HTTP GET using RestTemplate method getForObject
         * and returns the resource as JsonNode.
         * @param id the id of the user resource
         * @return the user resource as JsonNode
         * @throws IOException if received json string can not be parsed
         */
        public JsonNode getAsJsonNode(long id) throws IOException {
            String jsonString =  restTemplate.getForObject(REQUEST_URI + "/{id}",
                    String.class,
                    id);
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readTree(jsonString);
        }
        /**
         * Creates a user resource via HTTP POST using RestTemplate method getForObject.
         * @param user the employee to be created
         * @return the created user
         */
        public User postForObject(User user) {
            return restTemplate.postForObject(REQUEST_URI, user, User.class);
        }

        /**
         * Creates a user resource via HTTP POST using RestTemplate method getForLocation.
         * @param user the user to be created
         * @return the {@link URI} of the created user
         */
        public URI postForLocation(User user) {
            return restTemplate.postForLocation(REQUEST_URI, new HttpEntity<>(user));
        }

        /**
         * Creates a user resource via HTTP POST using RestTemplate method postForEntity.
         * @param newUser the user to be created
         * @return a ResponseEntity that wraps http status code, http headers and the body of type {@link User}
         */
        public ResponseEntity<User> postForEntity(User newUser) {
            MultiValueMap<String, String> headers = new HttpHeaders();
            headers.add("User-Agent", "UserRestUser demo class");
            headers.add("Accept-Language", "en-US");

            HttpEntity<User> entity = new HttpEntity<>(newUser, headers);

            return restTemplate.postForEntity(REQUEST_URI, entity, User.class);
        }

        /**
         * Updates a user resource via HTTP PUT using RestTemplate method put.
         * @param updatedUser the user to be updated
         */
        public void put(User updatedUser) {
            restTemplate.put(REQUEST_URI + "/{id}",
                    updatedUser,
                    Long.toString(updatedUser.getId()));
        }

        /**
         * Updates a user resource via HTTP PUT using RestTemplate method exchange.
         * @param updatedUser the user to be updated
         * @return a ResponseEntity that wraps http status code, http headers and the body of type {@link User}
         */
        public ResponseEntity<User> putWithExchange(User updatedUser) {
            return restTemplate.exchange(REQUEST_URI + "/{id}",
                    HttpMethod.PUT,
                    new HttpEntity<>(updatedUser),
                    User.class,
                    Long.toString(updatedUser.getId()));
        }

        /**
         * Deletes a user resurce via HTTP DELETE using RestTemplate method delete.
         * @param id the id of the user to be deleted
         */
        public void delete(long id) {
            restTemplate.delete(REQUEST_URI + "/{id}", Long.toString(id));
        }

        /**
         * Deletes a user resurce via HTTP DELETE using RestTemplate method exchange.
         * @param id the id of the employee to be deleted
         * @return a ResponseEntity that wraps http status code and http headers
         */
        public ResponseEntity<Void> deleteWithExchange(long id) {
            return restTemplate.exchange(REQUEST_URI + "/{id}",
                    HttpMethod.DELETE,
                    null,
                    Void.class,
                    Long.toString(id));
        }

        /**
         * Requests the built request URI via HTTP HEAD.
         * @return the HTTP headers for the requested URI.
         */
        public HttpHeaders headForHeaders() {
            return restTemplate.headForHeaders(REQUEST_URI);
        }

        /**
         * Requests the built request URI via HTTP OPTION.
         * @param id the employee to be requested with OPTION
         * @return all allowed HTTP methods for the requested URI
         */
        public Set<HttpMethod> optionsForAllow(long id) {
            return restTemplate.optionsForAllow(REQUEST_URI + "/{id}", Long.toString(id));
        }

    }