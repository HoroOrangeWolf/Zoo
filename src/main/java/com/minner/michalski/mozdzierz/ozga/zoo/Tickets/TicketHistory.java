package com.minner.michalski.mozdzierz.ozga.zoo.Tickets;

import com.minner.michalski.mozdzierz.ozga.zoo.User.User;
import lombok.*;

import javax.persistence.*;
import java.beans.ConstructorProperties;
import java.sql.Date;

@Table(name = "ticket_history")
@Entity
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

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public TicketHistory(Ticket ticket, Date date, Float price, User user) {
        this.ticket = ticket;
        this.date = date;
        this.price = price;
        this.user = user;
    }
}