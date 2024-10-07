package com.empresa.projetoapi.api.controller;

import com.empresa.projetoapi.model.User;
import com.empresa.projetoapi.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @Tag(name = "get", description="Veja dados dos usuarios")
    @GetMapping(value = "/user", produces = {"application/json", "application/xml"})
    public ResponseEntity<User> getUSer(@RequestParam Integer id) {
        User user = userService.getUser(id);

        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(user);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
    }

    @Tag(name = "get", description="Veja todos os usuarios")
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @Tag(name = "post", description="Crie um usuario")
    @PostMapping(value = "/user", consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            userService.createUser(user);

            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocorreu um erro na criação do usuário: " + e);
        }
    }



    @Tag(name = "put", description = "Atualize um usuario")
    @PutMapping(value = "/user", consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> editUser(@RequestBody User user) {
        try{
            userService.editUser(user);

            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado/atualizado com sucesso!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocorreu um erro no update do usuário: " + e);
        }
    }

    @Tag(name = "delete", description="Exclua um usuario")
    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(@RequestParam Integer id) {

        if(getUSer(id) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado para DELETE");
        }
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso");
    }
}
