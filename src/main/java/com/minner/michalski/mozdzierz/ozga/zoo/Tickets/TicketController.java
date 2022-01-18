package com.minner.michalski.mozdzierz.ozga.zoo.Tickets;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/ticket")
@AllArgsConstructor
public class TicketController {


    private final TicketService ticketService;




}
