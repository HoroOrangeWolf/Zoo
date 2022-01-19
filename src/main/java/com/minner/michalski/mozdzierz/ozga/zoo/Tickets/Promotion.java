package com.minner.michalski.mozdzierz.ozga.zoo.Tickets;

import com.minner.michalski.mozdzierz.ozga.zoo.Animal.Section;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "promotion")
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

    @OneToMany(mappedBy = "promotion", orphanRemoval = true)
    List<PromotionSection> sectionList;

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
