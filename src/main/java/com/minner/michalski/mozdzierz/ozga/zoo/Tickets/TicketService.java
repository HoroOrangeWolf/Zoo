package com.minner.michalski.mozdzierz.ozga.zoo.Tickets;


import com.minner.michalski.mozdzierz.ozga.zoo.Map.Path;
import com.minner.michalski.mozdzierz.ozga.zoo.Map.PathElement;
import com.minner.michalski.mozdzierz.ozga.zoo.Map.PathElementRepository;
import com.minner.michalski.mozdzierz.ozga.zoo.Map.PathRepository;
import com.minner.michalski.mozdzierz.ozga.zoo.User.User;
import com.minner.michalski.mozdzierz.ozga.zoo.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class TicketService {

    private TicketRepository repository;
    private PromotionRepository promotionRepository;
    private UserRepository userRepository;
    private PathRepository pathRepository;
    private PathElementRepository pathElementRepository;

    public void buyTicket(Long userId, Long promotionId, Date date){
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        Calendar calendarNow = Calendar.getInstance();

        calendarNow.setTime(new java.util.Date());

        if(calendar.get(Calendar.DAY_OF_MONTH) < calendarNow.get(Calendar.DAY_OF_MONTH) ||
                calendar.get(Calendar.MONTH) < calendarNow.get(Calendar.MONTH) ||
                calendar.get(Calendar.YEAR) < calendarNow.get(Calendar.YEAR))
            throw new IllegalStateException("You can't ticket before");

        Optional<Promotion> byIdPromotion = promotionRepository.findById(promotionId);

        if(byIdPromotion.isEmpty())
            throw  new IllegalStateException("Error!");

        Promotion promotion = byIdPromotion.get();

        Optional<User> byIdUser = userRepository.findById(userId);

        if(byIdUser.isEmpty())
            throw  new IllegalStateException("Error!");

        User user = byIdUser.get();

        List<PromotionSection> sectionList = promotion.getSectionList();

        Path path = new Path();

        pathRepository.save(path);

        List<PathElement> elements = new LinkedList<>();

        sectionList.forEach(f->{
            elements.add(new PathElement(f.getSection(), path, false));
        });

        pathElementRepository.saveAll(elements);

        Ticket ticket = new Ticket(promotion.getPrice(), true, false, new Date(new java.util.Date().getTime()), date, user, path);

        repository.save(ticket);
    }

    public void enterZoo(Long idTicket){
        Optional<Ticket> byId = repository.findById(idTicket);

        if(byId.isEmpty())
            throw new IllegalStateException("Invalid ticket");

        Ticket ticket = byId.get();

        if(ticket.getIsTicketActive())
            throw new IllegalStateException("You can't enter zoo!");

        ticket.setIsTicketActive(true);

        repository.save(ticket);
    }

    public void exitZoo(Long idTicket){
        Optional<Ticket> byId = repository.findById(idTicket);

        if(byId.isEmpty())
            return;

        Ticket ticket = byId.get();

        ticket.setIsTicketValidate(false);

        repository.save(ticket);
    }



}
