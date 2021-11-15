package com.minner.michalski.mozdzierz.ozga.zoo.Tickets;

import com.minner.michalski.mozdzierz.ozga.zoo.Tickets.Exceptions.TicketIsNotExistingException;
import com.minner.michalski.mozdzierz.ozga.zoo.User.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketHistoryRepository ticketHistoryRepository;

    public void addTicket(Ticket ticket){
        ticketRepository.save(ticket);
    }
    //Todo: Przerobić to inaczej
    public void buyTicket(User user, Ticket ticket){
        ticketHistoryRepository.save(new TicketHistory(ticket, new Date(new java.util.Date().getTime()), ticket.getPrice(), user));
    }

    public void removeTicket(Long id){
        if(ticketRepository.existsById(id))
            ticketRepository.deleteById(id);

        throw new TicketIsNotExistingException("User is not existing");
    }

    public void removeTicketHistory(Long id){
        ticketHistoryRepository.deleteById(id);
    }

    public List<Ticket> getTickets(){
        return ticketRepository.findAll();
    }
}
