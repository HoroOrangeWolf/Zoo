package com.minner.michalski.mozdzierz.ozga.zoo.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "request")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RequestGen")
    @SequenceGenerator(name = "RequestGen", sequenceName = "RequestSeq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;



    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Lob
    @Column(name = "text", nullable = false)
    private String text;


    public Request(Status status, String text) {
        this.status = status;
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Request request = (Request) o;
        return id != null && Objects.equals(id, request.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}