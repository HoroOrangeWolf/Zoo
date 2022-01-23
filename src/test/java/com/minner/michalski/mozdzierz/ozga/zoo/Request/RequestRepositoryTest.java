package com.minner.michalski.mozdzierz.ozga.zoo.Request;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
public class RequestRepositoryTest {

    @Autowired
    public RequestRepository requestRepository;

    @AfterEach
    public void clear(){
        requestRepository.deleteAll();;
    }

    @Test
    public void getRequests(){
        //given
        Request request = new Request(Status.NIEROZPATRZONY,"Prosze o zwolnienie");
        Request request2 = new Request(Status.NIEROZPATRZONY,"Prosze o zwolnienie");

        //when

        requestRepository.save(request);
        requestRepository.save(request2);

        List<Request> requestByStatus = requestRepository.getRequestByStatus(Status.NIEROZPATRZONY);
        //then
        assertArrayEquals(requestByStatus.toArray( new Request[0]), new Request[]{request, request2});

    }

}
