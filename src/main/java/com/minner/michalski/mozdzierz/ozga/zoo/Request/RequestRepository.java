package com.minner.michalski.mozdzierz.ozga.zoo.Request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("SELECT c FROM Request c WHERE c.status=?1")
    List<Request> getRequestByStatus(Status status);
}
