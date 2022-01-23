package com.minner.michalski.mozdzierz.ozga.zoo.Animal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long> {

    @Query("SELECT c FROM Section c WHERE c.isOnTheMap = true")
    List<Section> getAllSectionsOnMap();

}
