package com.minner.michalski.mozdzierz.ozga.zoo.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PathElementRepository extends JpaRepository<PathElement, Long> {
}
