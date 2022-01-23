package com.minner.michalski.mozdzierz.ozga.zoo.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "path")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Path {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "path_id_generator")
    @SequenceGenerator(name = "path_id_generator", sequenceName = "path_id_sequence", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "path")
    private List<PathElement> pathElements;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Path path = (Path) o;
        return id != null && Objects.equals(id, path.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
