package com.minner.michalski.mozdzierz.ozga.zoo.Request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query(value = "SELECT c FROM Request c WHERE c.status LIKE 'NIEROZPATRZONY' ORDER BY c.date ASC")
    List<Request> getNextRequests();
}
