package com.minner.michalski.mozdzierz.ozga.zoo.User;

import com.minner.michalski.mozdzierz.ozga.zoo.User.Exception.UserNotExistException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User getUser(Long id){
        return repository.getById(id);
    }

    public void addUser(User user){

        Optional<User> userByEmail = repository.getUserByEmail(user.getEmail());

        if(userByEmail.isPresent())
            throw new IllegalStateException("User identified by this email exists in database!");

        repository.save(user);
    }

    public void updateUser(User user){

        if(!repository.existsById(user.getId()))
            throw new IllegalStateException("User is not existing");

        repository.save(user);
    }

    public void deleteUserById(Long id){
        if(!repository.existsById(id))
            throw new UserNotExistException("User is not existing");

        repository.deleteById(id);
    }

    public void updatePassword(Long id, String password){
        Optional<User> optionalUser = repository.findById(id);

        if(optionalUser.isEmpty())
            throw new IllegalStateException("User by : " + id + " is not existing!");

        User toUpdate = optionalUser.get();

        toUpdate.setPassword(password);

        repository.save(toUpdate);
    }

    public void updateEmail(Long id, String email){
        Optional<User> optionalUser = repository.findById(id);

        if(optionalUser.isEmpty())
            throw new IllegalStateException("User by : " + id + " is not existing!");

        User toUpdate = optionalUser.get();

        toUpdate.setEmail(email);

        repository.save(toUpdate);
    }

    public boolean isUserBOK(Long id){
        Optional<User> optionalUser = repository.findById(id);

        if(optionalUser.isEmpty())
            throw new IllegalStateException("User by : " + id + " is not existing!");

        return optionalUser.get().isBokManager();
    }
}
