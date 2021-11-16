package com.minner.michalski.mozdzierz.ozga.zoo.Animal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "section")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sectionGen")
    @SequenceGenerator(name = "sectionGen", sequenceName = "sectionSeq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "is_on_the_map", nullable = false)
    private Boolean isOnTheMap = false;

    @Column(name = "mapx", nullable = false)
    private Float mapx;

    @Column(name = "mapy", nullable = false)
    private Float mapy;

    @Column(name = "section_url_image", length = 1000)
    private String sectionUrlImage;


}