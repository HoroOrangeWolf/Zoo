package com.minner.michalski.mozdzierz.ozga.zoo.Request;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "request")
@Entity
@Getter
@Setter
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

}