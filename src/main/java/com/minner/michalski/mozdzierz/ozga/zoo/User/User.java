package com.minner.michalski.mozdzierz.ozga.zoo.User;

import com.minner.michalski.mozdzierz.ozga.zoo.Tickets.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Table(name = "user")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nick", nullable = false)
    private String nick;

    @Column(name = "password", nullable = false)
    private String password;



    @Column(name = "is_bok_manager", nullable = false)
    private Boolean isBokManager;

    @Column(name = "last_login", nullable = false, columnDefinition = "long default 0")
    private Date lastLogin;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> ticketHistories;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

}