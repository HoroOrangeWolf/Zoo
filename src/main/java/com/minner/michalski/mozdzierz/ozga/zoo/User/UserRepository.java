package com.minner.michalski.mozdzierz.ozga.zoo.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    @Query("SELECT c FROM User c WHERE c.nick LIKE ?1")
    Optional<User> getUserByName(String nick);



}
