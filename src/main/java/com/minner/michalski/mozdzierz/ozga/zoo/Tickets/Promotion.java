package com.minner.michalski.mozdzierz.ozga.zoo.Tickets;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "promotion_id_generator")
    @SequenceGenerator(name = "promotion_id_generator", sequenceName = "promotion_sequence")
    private Long id;

    @Column(name = "price", nullable = false)
    private BigDecimal price;
}
