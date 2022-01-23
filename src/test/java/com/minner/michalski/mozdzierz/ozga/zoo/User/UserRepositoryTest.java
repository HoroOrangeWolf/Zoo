package com.minner.michalski.mozdzierz.ozga.zoo.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
public class UserRepositoryTest {

    @Autowired
    private  UserRepository repository;

    @AfterEach
    public void after(){
        repository.deleteAll();
    }

    @Test
    public void getUserBeEmail(){
        //given
        User user = new User("Wojciech",
                "TestPass",
                false,
                new Date(new java.util.Date().getTime()),
                "wojciech@gmail.com" );

        //when
        repository.save(user);

        Optional<User> reuturned = repository.getUserByEmail(user.getEmail());

        //then

        assertTrue(reuturned.isPresent());

        assertEquals(reuturned.get(), user);

    }


}
