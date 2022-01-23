package com.minner.michalski.mozdzierz.ozga.zoo.User;

import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@RestController
@RequestMapping(path = "/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping
    public void registerUser(@Valid @RequestBody User user){
        service.addUser(user);
    }

    @PutMapping("/{userId}/changeEmail")
    public void changeEmail(@PathVariable("userId") Long id,@RequestBody String email){
        service.updateEmail(id, email);
    }

    @PutMapping("/{userId}/changePassword")
    public void changePassword(@PathVariable("userId") Long id,@Valid @Length(min=3, max = 12) @RequestBody String password){
        service.updatePassword(id, password);
    }

    @PutMapping
    public void updateUser(@Valid @RequestBody User user){
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
