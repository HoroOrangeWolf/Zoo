package com.minner.michalski.mozdzierz.ozga.zoo.Tickets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Table(name = "ticket_history")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticketHistory_id_generator")
    @SequenceGenerator(name = "ticketHistory_id_generator", sequenceName = "ticketHistory_sequence", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "price", nullable = false)
    private Float price;

}