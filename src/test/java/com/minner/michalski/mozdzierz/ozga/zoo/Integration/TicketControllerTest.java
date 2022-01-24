package com.minner.michalski.mozdzierz.ozga.zoo.Integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.minner.michalski.mozdzierz.ozga.zoo.Animal.Section;
import com.minner.michalski.mozdzierz.ozga.zoo.Animal.SectionRepository;
import com.minner.michalski.mozdzierz.ozga.zoo.Map.Path;
import com.minner.michalski.mozdzierz.ozga.zoo.Map.PathRepository;
import com.minner.michalski.mozdzierz.ozga.zoo.Request.RequestRepository;
import com.minner.michalski.mozdzierz.ozga.zoo.Tickets.*;
import com.minner.michalski.mozdzierz.ozga.zoo.Tickets.Exceptions.PromotionSectionRepository;
import com.minner.michalski.mozdzierz.ozga.zoo.User.User;
import com.minner.michalski.mozdzierz.ozga.zoo.User.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.NestedServletException;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final static String url = "http://localhost";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PathRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private PromotionSectionRepository promotionSectionRepository;

    @AfterEach
    public void clear(){
        ticketRepository.deleteAll();
        userRepository.deleteAll();
        repository.deleteAll();
        promotionSectionRepository.deleteAll();
        promotionRepository.deleteAll();
        sectionRepository.deleteAll();
    }

    @Test
    void enterZoo() throws Exception {
        //given
        Path path = new Path();

        repository.save(path);

        User user = new User("Wojciech",
                "TestPass",
                false,
                new Date(new java.util.Date().getTime()),
                "wojciech@gmail.com" );

        userRepository.save(user);

        Ticket ticket = new Ticket(BigDecimal.valueOf(25.f),true, false, new Date(new java.util.Date().getTime()), new Date(new java.util.Date().getTime()), user, path);

        ticketRepository.save(ticket);

        //when
        ResultActions resultActions = mockMvc.perform(put(url + "/api/v1/ticket/" + ticket.getId() + "/enter"))
                .andExpect(status().isOk())
                .andDo(print());


        //then
        Optional<Ticket> byId = ticketRepository.findById(ticket.getId());
        assertTrue(byId.isPresent());

        Ticket ticket1 = byId.get();

        assertTrue(ticket1.getIsTicketActive());
    }

    @Test
    void enterZooWithActiveTicket() throws Exception {
        //given
        Path path = new Path();

        repository.save(path);

        User user = new User("Wojciech",
                "TestPass",
                false,
                new Date(new java.util.Date().getTime()),
                "wojciech@gmail.com" );

        userRepository.save(user);

        Ticket ticket = new Ticket(BigDecimal.valueOf(25.f),true, true, new Date(new java.util.Date().getTime()), new Date(new java.util.Date().getTime()), user, path);

        ticketRepository.save(ticket);

        //when

        assertThatThrownBy(()->{
            try{
                ResultActions resultActions = mockMvc.perform(put(url + "/api/v1/ticket/" + ticket.getId() + "/enter"))
                        .andExpect(status().isOk())
                        .andDo(print());
            }catch (NestedServletException e){
                throw e.getCause();
            }

        }).isInstanceOf(IllegalStateException.class).hasMessage("You can't enter zoo!");
    }

    @Test
    void enterZooWithInvalidTicket() throws Exception {
        //given

        //when

        assertThatThrownBy(()->{
            try{
                ResultActions resultActions = mockMvc.perform(put(url + "/api/v1/ticket/" + 15L + "/enter"))
                        .andExpect(status().isOk())
                        .andDo(print());
            }catch (NestedServletException e){
                throw e.getCause();
            }

        }).isInstanceOf(IllegalStateException.class).hasMessage("Invalid ticket");
    }

    @Test
    void exitZoo() throws Exception {
        //given
        Path path = new Path();

        repository.save(path);

        User user = new User("Wojciech",
                "TestPass",
                false,
                new Date(new java.util.Date().getTime()),
                "wojciech@gmail.com" );

        userRepository.save(user);

        Ticket ticket = new Ticket(BigDecimal.valueOf(25.f),true, true, new Date(new java.util.Date().getTime()), new Date(new java.util.Date().getTime()), user, path);

        ticketRepository.save(ticket);

        //when
        ResultActions resultActions = mockMvc.perform(put(url + "/api/v1/ticket/" + ticket.getId() + "/exit"))
                .andExpect(status().isOk())
                .andDo(print());


        //then
        Optional<Ticket> byId = ticketRepository.findById(ticket.getId());
        assertTrue(byId.isPresent());

        Ticket ticket1 = byId.get();

        assertFalse(ticket1.getIsTicketValidate());
    }

    @Test
    void buyTicket() throws Exception {
        //given
        Section section2 = new Section("Ptaki", "Papuga ",
                false, 1.f, 1.f, "brak");


        sectionRepository.save(section2);

        Promotion promotion = new Promotion();

        promotion.setPrice(BigDecimal.valueOf(250.f));

        promotionRepository.save(promotion);

        PromotionSection promotionSection = new PromotionSection();

        promotionSection.setPromotion(promotion);
        promotionSection.setSection(section2);

        promotionSectionRepository.save(promotionSection);



        User user = new User("Wojciech",
                "TestPass",
                false,
                new Date(new java.util.Date().getTime()),
                "wojciech@gmail.com" );

        userRepository.save(user);

        //when
        ResultActions resultActions = mockMvc.perform(post(url + "/api/v1/ticket/user/" + user.getId()).param("promotion", promotion.getId().toString()).param("reservationTime", "2022-04-12"))
                .andExpect(status().isOk())
                .andDo(print());


        //then
    }

}
