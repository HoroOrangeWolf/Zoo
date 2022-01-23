package com.minner.michalski.mozdzierz.ozga.zoo.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
public class UserServiceTest {

    @Autowired
    public UserService service;

    @Autowired
    public UserRepository userRepository;

    @AfterEach
    public void clear(){
        userRepository.deleteAll();
    }

    @Test
    public void addUser(){
        //given
        User user = new User("Wojciech",
                "TestPass",
                false,
                new Date(new java.util.Date().getTime()),
                "wojciech@gmail.com" );
        //when
        service.addUser(user);

        //then
        assertTrue(userRepository.findAll().stream().anyMatch(f->f.getEmail().equals(user.getEmail())));
    }

    @Test
    public void addUserWithExistingEmail(){
        //given
        User user = new User("Wojciech",
                "TestPass",
                false,
                new Date(new java.util.Date().getTime()),
                "wojciech@gmail.com" );

        User userTwo = new User("Jacek",
                "TestPass",
                false,
                new Date(new java.util.Date().getTime()),
                "wojciech@gmail.com" );
        //when
        service.addUser(user);

        //then
        assertThatThrownBy(()->{
            service.addUser(userTwo);
        }).isInstanceOf(IllegalStateException.class).hasMessage("User identified by this email exists in database!");
    }

    @Test
    public void updateUser(){
        //given
        User user = new User("Wojciech",
                "TestPass",
                false,
                new Date(new java.util.Date().getTime()),
                "wojciech@gmail.com" );

        User updated = new User("Wojciech12",
                "TestPass12",
                false,
                new Date(new java.util.Date().getTime()),
                "jacek@gmail.com" );
        //when
        service.addUser(user);

        updated.setId(user.getId());

        service.updateUser(updated);
        //then

        Optional<User> byId = userRepository.findById(user.getId());

        assertTrue(byId.isPresent());

        assertEquals(updated, byId.get());
    }

    @Test
    public void updateUserNotExistingId(){
        //given
        Long id = -1L;

        User updated = new User("Wojciech12",
                "TestPass12",
                false,
                new Date(new java.util.Date().getTime()),
                "jacek@gmail.com" );
        //when
        updated.setId(id);
        //then
        assertThatThrownBy(()->{
            service.updateUser(updated);
        }).isInstanceOf(IllegalStateException.class).hasMessage("User is not existing");

    }

    @Test
    public void updatePassword(){
        //given
        User user = new User("Wojciech",
                "TestPass",
                false,
                new Date(new java.util.Date().getTime()),
                "wojciech@gmail.com" );

        String password = "new_password";
        //when
        userRepository.save(user);

        service.updatePassword(user.getId(), password);
        //then

        Optional<User> byId = userRepository.findById(user.getId());

        assertTrue(byId.isPresent());

        assertEquals(password, byId.get().getPassword());
    }

    @Test
    public void updatePasswordNotExistingUser(){
        //given
        Long id = -1L;
        String password = "new_password";
        //when

        assertThatThrownBy(()->{
            service.updatePassword(id, password);
        }).isInstanceOf(IllegalStateException.class).hasMessage("User by : " + id + " is not existing!");
        //then

    }

    @Test
    public void updateEmail(){
        //given
        User user = new User("Wojciech",
                "TestPass",
                false,
                new Date(new java.util.Date().getTime()),
                "wojciech@gmail.com" );

        String new_email = "jacek@gmail.com";
        //when
        userRepository.save(user);

        service.updateEmail(user.getId(), new_email);
        //then

        Optional<User> byId = userRepository.findById(user.getId());

        assertTrue(byId.isPresent());

        assertEquals(new_email, byId.get().getEmail());
    }

    @Test
    public void updateEmailNotExistingUser(){
        //given
        Long id = -1L;
        String email = "jacus@gmail.com";
        //when

        assertThatThrownBy(()->{
            service.updatePassword(id, email);
        }).isInstanceOf(IllegalStateException.class).hasMessage("User by : " + id + " is not existing!");
        //then

    }

    @Test
    public void isBokUser(){
        //given
        User user = new User("Wojciech",
                "TestPass",
                true,
                new Date(new java.util.Date().getTime()),
                "wojciech@gmail.com" );

        String new_email = "jacek@gmail.com";
        //when
        userRepository.save(user);

        //then

        assertTrue(service.isUserBOK(user.getId()));
    }

    @Test
    public void isBokUserNotExistingUser(){
        //given
        Long id = -1L;
        String email = "jacus@gmail.com";
        //when

        assertThatThrownBy(()->{
            service.isUserBOK(id);
        }).isInstanceOf(IllegalStateException.class).hasMessage("User by : " + id + " is not existing!");
        //then

    }


}
