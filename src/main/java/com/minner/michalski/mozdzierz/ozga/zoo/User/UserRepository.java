package com.minner.michalski.mozdzierz.ozga.zoo.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT c FROM User c WHERE c.email LIKE ?1")
    Optional<User> getUserByEmail(String email);

}
