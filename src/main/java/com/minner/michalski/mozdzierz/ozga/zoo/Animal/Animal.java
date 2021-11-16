package com.minner.michalski.mozdzierz.ozga.zoo.Animal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "animal")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AnimalGen")
    @SequenceGenerator(name = "AnimalGen", sequenceName = "AnimalSeq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;


}