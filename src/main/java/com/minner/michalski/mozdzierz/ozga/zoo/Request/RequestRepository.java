package com.minner.michalski.mozdzierz.ozga.zoo.Request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("SELECT c FROM Request c WHERE c.status=?1")
    List<Request> getRequestByStatus(Status status);
}
