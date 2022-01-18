package com.minner.michalski.mozdzierz.ozga.zoo.Map;


import com.minner.michalski.mozdzierz.ozga.zoo.Animal.Section;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pathelement")
public class PathElement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "path_element_id_generator")
    @SequenceGenerator(name = "path_element_id_generator", sequenceName = "path_element_id_sequence", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @Column(name="isVisited")
    private Boolean isVisited = false;

    @ManyToOne
    @JoinColumn(name = "path_id")
    private Path path;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PathElement that = (PathElement) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
