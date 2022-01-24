package com.minner.michalski.mozdzierz.ozga.zoo.Tickets;

import com.minner.michalski.mozdzierz.ozga.zoo.Animal.Section;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PromotionSection {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "promotion_section_generator")
    @SequenceGenerator(name = "promotion_section_generator", sequenceName = "promotion_section_sequence", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="section_id")
    private Section section;


    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

}
