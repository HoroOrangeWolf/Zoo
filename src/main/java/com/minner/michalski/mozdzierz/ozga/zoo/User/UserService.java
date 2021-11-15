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
        repository.save(user);
    }

    public void updateUser(User user){
        if(!repository.existsById(user.getId()))
            throw new UserNotExistException("User is not existing");

        repository.save(user);
    }

    public void deleteUserById(Long id){
        if(!repository.existsById(id))
            throw new UserNotExistException("User is not existing");
        repository.deleteById(id);
    }

    public User getUserByName(String nick){

        Optional<User> user = repository.getUserByName(nick);

        if(user.isEmpty())
            throw new UserNotExistException(String.format("User %s is not existing", nick));

        return user.get();
    }

    public boolean isUserBOK(Long id){
        return getUser(id).getIsBokManager();
    }
}
