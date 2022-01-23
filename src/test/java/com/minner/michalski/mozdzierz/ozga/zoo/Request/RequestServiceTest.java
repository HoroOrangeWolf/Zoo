package com.minner.michalski.mozdzierz.ozga.zoo.Request;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
public class RequestServiceTest {

    @Autowired
    public RequestRepository requestRepository;

    @Autowired
    public RequestService service;

    @AfterEach
    public void clear(){
        requestRepository.deleteAll();;
    }

    @Test
    public void addRequest(){
        //given
        Request request = new Request(Status.NIEROZPATRZONY,"Prosze o zwolnienie");

        //when
        service.addRequest(request);

        //then
        List<Request> all = requestRepository.findAll();

        assertTrue(all.stream().anyMatch(f->f.getText().equals(request.getText()) && f.getStatus().equals(request.getStatus())));
    }

    @Test
    public void removeRequest(){
        //given
        Request request = new Request(Status.NIEROZPATRZONY,"Prosze o zwolnienie");

        //when
        requestRepository.save(request);

        service.removeRequest(request.getId());

        //then
        List<Request> all = requestRepository.findAll();

        assertFalse(all.stream().anyMatch(f->f.getText().equals(request.getText()) && f.getStatus().equals(request.getStatus())));
    }

    @Test
    public void updateRequest(){
        //given
        Request request = new Request(Status.NIEROZPATRZONY,"Prosze o zwolnienie");

        Request toUpdate = new Request(Status.ROZPATRYWANY,"Prosze o nie zwolninie");
        //when
        requestRepository.save(request);

        toUpdate.setId(request.getId());

        service.updateRequest(toUpdate);

        //then
        Optional<Request> byId = requestRepository.findById(request.getId());

        assertTrue(byId.isPresent());

        assertEquals(toUpdate, byId.get());

    }

}
