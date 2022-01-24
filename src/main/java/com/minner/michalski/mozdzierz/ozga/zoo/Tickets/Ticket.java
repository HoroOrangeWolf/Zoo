package com.minner.michalski.mozdzierz.ozga.zoo.Tickets;

import com.minner.michalski.mozdzierz.ozga.zoo.Map.Path;
import com.minner.michalski.mozdzierz.ozga.zoo.User.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;

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

    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "path_id")
    private Path path;

    public Ticket(BigDecimal price, Boolean isTicketValidate, Boolean isTicketActive, Date buyDate, Date reservationTime, User user, Path path) {
        this.price = price;
        this.isTicketValidate = isTicketValidate;
        this.isTicketActive = isTicketActive;
        this.buyDate = buyDate;
        this.reservationTime = reservationTime;
        this.user = user;
        this.path = path;
    }
}