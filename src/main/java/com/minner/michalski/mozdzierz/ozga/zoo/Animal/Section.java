package com.minner.michalski.mozdzierz.ozga.zoo.Animal;

import com.minner.michalski.mozdzierz.ozga.zoo.Map.PathElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Table(name = "section")
@Entity
@Getter
@Setter
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

    public Section(String name, String description, Boolean isOnTheMap, Float mapx, Float mapy, String sectionUrlImage) {
        this.name = name;
        this.description = description;
        this.isOnTheMap = isOnTheMap;
        this.mapx = mapx;
        this.mapy = mapy;
        this.sectionUrlImage = sectionUrlImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Section section = (Section) o;
        return id != null && Objects.equals(id, section.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}