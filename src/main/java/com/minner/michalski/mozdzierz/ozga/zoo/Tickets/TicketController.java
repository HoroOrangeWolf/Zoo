package com.minner.michalski.mozdzierz.ozga.zoo.Tickets;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("api/v1/ticket")
@AllArgsConstructor
public class TicketController {


    private final TicketService ticketService;

    @PutMapping("/{idTicket}/enter")
    public void enterZoo(@PathVariable("idTicket") Long idTicket){
        ticketService.enterZoo(idTicket);
    }

    @PutMapping("/{idTicket}/exit")
    public void exitZoo(@PathVariable("idTicket") Long idTicket){
        ticketService.exitZoo(idTicket);
    }


    @PostMapping("/user/{userId}")
    public void buyTicket(@PathVariable("userId") Long userId, @RequestParam("promotion") Long promotionId, @RequestParam("reservationTime") String date)
    {

        Date dateFormated = Date.valueOf(date);

        ticketService.buyTicket(userId, promotionId, dateFormated);
    }


}
