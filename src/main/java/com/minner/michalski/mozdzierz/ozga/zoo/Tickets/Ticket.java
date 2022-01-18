package com.minner.michalski.mozdzierz.ozga.zoo.Tickets;

import com.minner.michalski.mozdzierz.ozga.zoo.Map.Path;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Table(name = "ticket")
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_id_generator")
    @SequenceGenerator(name = "ticket_id_generator", sequenceName = "ticket_sequence", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "isTicketValidate", nullable = false)
    private Boolean isTicketValidate = false;

    @Column(name = "isTicketActive", nullable = false)
    private Boolean isTicketActive = true;

    @Column(name = "buyDate", nullable = false)
    private Date buyDate;

    @Column(name = "reservationTime", nullable = false)
    private Date reservationTime;


    @OneToOne
    @JoinColumn(name = "path_id")
    private Path path;

}