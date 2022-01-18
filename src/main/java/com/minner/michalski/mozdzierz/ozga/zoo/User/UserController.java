package com.minner.michalski.mozdzierz.ozga.zoo.User;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public void registerUser(@RequestBody User user){
        service.addUser(user);
    }

    @PutMapping("/{userId}/changeEmail")
    public void changeEmail(@PathVariable("userid") Long id,@RequestBody User user){
        user.setId(user.getId());
        service.updateEmail(user);
    }

    @PutMapping
    public void changePassword(@PathVariable("userid") Long id,@RequestBody User user){
        user.setId(user.getId());
        service.updatePassword(user);
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
