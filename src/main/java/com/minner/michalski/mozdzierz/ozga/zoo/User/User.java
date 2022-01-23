package com.minner.michalski.mozdzierz.ozga.zoo.User;

import com.minner.michalski.mozdzierz.ozga.zoo.Tickets.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "user")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")
    @SequenceGenerator(name = "user_id_generator", sequenceName = "user_id_sequence", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Length(min = 3, max = 12)
    @Column(name = "nick", nullable = false)
    private String nick;

    @Length(min=3, max = 12)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_bok_manager", nullable = false)
    private boolean isBokManager;

    @Column(name = "last_login", nullable = false)
    private Date lastLogin;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> ticketHistories = new ArrayList<>();

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    public User(String nick, String password, Boolean isBokManager, Date lastLogin, String email) {
        this.nick = nick;
        this.password = password;
        this.isBokManager = isBokManager;
        this.lastLogin = lastLogin;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}