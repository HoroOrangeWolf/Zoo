package com.minner.michalski.mozdzierz.ozga.zoo.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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
    List<PathElement> pathElements;


}
