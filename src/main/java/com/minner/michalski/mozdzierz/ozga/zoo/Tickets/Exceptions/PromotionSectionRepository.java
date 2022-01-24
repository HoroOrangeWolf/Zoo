package com.minner.michalski.mozdzierz.ozga.zoo.Tickets.Exceptions;

import com.minner.michalski.mozdzierz.ozga.zoo.Tickets.Promotion;
import com.minner.michalski.mozdzierz.ozga.zoo.Tickets.PromotionSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionSectionRepository extends JpaRepository<PromotionSection, Long> {
}
