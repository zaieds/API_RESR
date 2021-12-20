package com.user.register_user.rest;

import com.user.register_user.models.User;
import com.user.register_user.service.UserService;
import com.user.register_user.util.CtrlPreconditions;
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

    //ajouter un utilisateur
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> create(@RequestBody User user ){
         String msg =  userService.create(user)  ;
            return ResponseEntity.status(HttpStatus.OK).body(msg);

    }

    //chercher tout les utilisateur
    @GetMapping
    public List<User> findAll(){
        return userService.findAll();
    }

    // chercher un utilisateur par son id

    @GetMapping("/{id}")
    public User findById(@PathVariable("id") String id)
    {
        CtrlPreconditions.checkFound(userService.findById(id));
        return userService.findById(id);
    }

    //mettre à jour tout l'utilisateur'
    @PutMapping("/{idUser}")
    @ResponseStatus(code = HttpStatus.OK)
    public void update(@PathVariable("idUser") String id, @RequestBody User user){
        CtrlPreconditions.checkFound(userService.findById(id));
        userService.update(id,user);
    }

    //mettre à jour partiel des données de l'utilisateur'
    @PatchMapping("/{idUser}")
    @ResponseStatus(code = HttpStatus.OK)
    public void PartialUpdate(@PathVariable("idUser") String id, Map<String, Object> updates){
        CtrlPreconditions.checkFound(userService.findById(id));
        userService.PartialUpdate(id,updates);
    }


    //Supprimer un utilisateur
    @DeleteMapping("/{idUser}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<String> delete(@PathVariable ("idUser") String id){
        CtrlPreconditions.checkFound(userService.findById(id));
        String msg =  userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(msg);

    }
}
