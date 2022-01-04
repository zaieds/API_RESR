package com.user.register_user.rest;

import com.user.register_user.dao.UserRepository;
import com.user.register_user.exception.RessourceAlreadyExistsException;
import com.user.register_user.exception.RessourceNotFoundException;
import com.user.register_user.models.User;
import com.user.register_user.service.UserService;
import com.user.register_user.util.CtrlPreconditions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    //create user
    @Operation(summary = "Post User",description = "Post a new user",tags = "Post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post a user", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "500", description = "Bad request",content = @Content)})
    @PostMapping
    public ResponseEntity<String> create(@RequestBody User user) {

        String msg = userService.create(user);
        return new ResponseEntity<>(msg, HttpStatus.CREATED);

    }

    //get all users
    @Operation(summary = "Get All users",description = "Get a list  of users",tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "users not Found",content = @Content)})
    @GetMapping
    public ResponseEntity<List> findAll() throws RessourceNotFoundException {
        return new ResponseEntity<List>((List) userService.findAll(), HttpStatus.OK);
    }

    // get user by ID
    @Operation(summary = "Get user",description = "Get information of user",tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found  user", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "user not Found",content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") String id) throws RessourceNotFoundException {
        try {
            return new ResponseEntity(userService.findById(id), HttpStatus.OK);
        } catch (RessourceAlreadyExistsException ressourceAlreadyExistsException) {
            throw new RessourceNotFoundException("User with id-" + id +" Not found");

        }

    }

    //mettre à jour tout l'utilisateur

    @Operation(summary = "Update user",description = "update all information of  user",tags = "Put")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "update  user", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "user not Found",content = @Content)})
    @PutMapping("/{idUser}")
    public ResponseEntity update(@PathVariable("idUser") String id, @RequestBody User user) {
        try {
            return new ResponseEntity<>(userService.update(id, user), HttpStatus.OK);
        }catch (RessourceNotFoundException exception){
            throw new RessourceNotFoundException("User with id-" + id +" Not found");
        }
    }

    //mettre à jour partiel des données de l'utilisateur
    @Operation(summary = "partial Update user",description = "partial update of  user",tags = "Patch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "partial update  user", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "user not Found",content = @Content)})
    @PatchMapping("/{idUser}")
    public ResponseEntity PartialUpdate(@PathVariable("idUser") String id, Map<String, Object> updates, User user) {
        try {
            return new ResponseEntity<>(userService.PartialUpdate(id, updates,user), HttpStatus.OK);
    }catch (RessourceNotFoundException exception){
        throw new RessourceNotFoundException("User with id-" + id +" Not found");
    }
    }


    //Supprimer un utilisateur
    @Operation(summary = "delete user",description = "delete  user",tags = "Delete")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "delete  user", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "user not Found",content = @Content)})
    @DeleteMapping("/{idUser}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<String> delete(@PathVariable("idUser") String id) {
        CtrlPreconditions.checkFound(userService.findById(id));
        String msg = userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(msg);

    }


}
