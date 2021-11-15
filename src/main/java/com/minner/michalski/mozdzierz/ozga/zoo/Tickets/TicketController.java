package com.minner.michalski.mozdzierz.ozga.zoo.Tickets;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("api/v1/ticket")
@AllArgsConstructor
public class TicketController {


    private final TicketService ticketService;

    public List<Ticket> getTickets(){
        return ticketService.getTickets();
    }

    @PostConstruct
    public void test(){
        System.out.printf("Tickets in db: %d%n", ticketService.getTickets().size());
    }

}
