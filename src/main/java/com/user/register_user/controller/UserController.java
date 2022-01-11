package com.user.register_user.controller;

import com.user.register_user.exception.RessourceNotFoundException;
import com.user.register_user.models.User;
import com.user.register_user.service.UserService;

import com.user.register_user.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {



    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * create user
     *
     * @param user user
     * @return response
     *
     */
    @Operation(summary = "Create User",description = "Create a new user",tags = "Post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create user", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "201", description = "Bad request",content = @Content)})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@Valid @RequestBody User user) {
        logger.debug("inside UserController.create() method");
        String response = userService.create(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    /**
     *  get all users
     *
     * @return the list
     *
     */
//Annotation Swagger
    @Operation(summary = "Get All users",description = "Get list of users",tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "users not Found",content = @Content)})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> findAll(){

        logger.debug("inside UserController.findAll() method");
        return userService.findAll();
    }

    /**
     * Get user by id
     *
     * @param userId the user id
     * @return user by id
     * @see ResponseEntity
     * @see User
     * @throws RessourceNotFoundException com.user.register_user.exception. ressource not found exception
     */
    @Operation(summary = "Get user",description = "Get user details",tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found user", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "user not Found",content = @Content)})
    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> findById(@PathVariable(value = "id") Long userId) throws RessourceNotFoundException {
        logger.debug("inside UserController.findById() method");
        return new ResponseEntity(userService.findById(userId), HttpStatus.OK);


    }

    /**
     * update details user
     *
     * @param userId the user id
     * @param userDetails the user details
     * @return {@link ResponseEntity}
     * @throws RessourceNotFoundException com.user.register_user.exception. ressource not found exception
     */
    @Operation(summary = "Update",description = "update user  by id",tags = "Put")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "update  user", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "user not Found",content = @Content),
            @ApiResponse(responseCode = "500", description = "Bad Request",content = @Content)})

    @PutMapping(path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@PathVariable("id") Long userId, @Valid @RequestBody User userDetails) throws RessourceNotFoundException{
            logger.debug("inside UserController.update() method");
            try{
                String userUpdate = userService.update(userId, userDetails);
                return  ResponseEntity.ok(userUpdate);
            }catch (Exception e){
                e.printStackTrace();
                return (ResponseEntity<String>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
            }


    }


    /**
     *  partial update details user
     *
     * @param userId the user id
     * @param updates updates
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     */
    @Operation(summary = "partial update",description = "partial update user details  by id",tags = "Patch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "partial update user", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "user not Found",content = @Content)})
    @PatchMapping("/{id}")
    public ResponseEntity PartialUpdate(@PathVariable("id") Long userId, @RequestBody Map<String, Object> updates) {
            logger.debug("inside UserController.PartialUpdate() method");
            return new ResponseEntity<>(userService.PartialUpdate(userId, updates), HttpStatus.OK);
        }



    /**
     * delete user by id
     *
     * @param userId userId
     * @return {@link Map}
     * @see Map
     * @see String
     * @see Boolean
     * @throws RessourceNotFoundException com.user.register_user.exception. ressource not found exception
     */
    @Operation(summary = "delete",description = "delete user by id",tags = "Delete")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "delete  user", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "user not Found",content = @Content)})
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, Boolean> delete(@PathVariable("id") Long userId) throws RessourceNotFoundException{
        logger.debug("inside UserController.delete() method");
        return  userService.delete(userId);

    }

}
