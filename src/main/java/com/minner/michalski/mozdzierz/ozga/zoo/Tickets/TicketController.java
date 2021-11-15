package com.minner.michalski.mozdzierz.ozga.zoo.Tickets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("api/v1/tickets")
public class TicketController {


    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService service){
        ticketService = service;
    }

    public List<Ticket> getTickets(){
        return ticketService.getTickets();
    }

    @PostConstruct
    public void test(){
        System.out.println(String.format("Tickets in db: %d", ticketService.getTickets().size()));
    }

}
