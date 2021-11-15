package com.minner.michalski.mozdzierz.ozga.zoo.User;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public void addUser(@RequestBody User user){
        service.addUser(user);
    }

    @PutMapping
    public void updateUser(@RequestBody User user){
        service.updateUser(user);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") Long id){
        return service.getUser(id);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long id){
        service.deleteUserById(id);
    }



}
