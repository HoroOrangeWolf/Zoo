package com.minner.michalski.mozdzierz.ozga.zoo.Tickets;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Table
@Entity(name = "promotion")
@ToString
@AllArgsConstructor
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "promotion_id_generator")
    @SequenceGenerator(name = "promotion_id_generator", sequenceName = "promotion_sequence")
    private Long id;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Promotion promotion = (Promotion) o;
        return id != null && Objects.equals(id, promotion.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
