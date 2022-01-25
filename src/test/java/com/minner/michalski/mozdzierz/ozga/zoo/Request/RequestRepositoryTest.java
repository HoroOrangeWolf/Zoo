package com.minner.michalski.mozdzierz.ozga.zoo.Request;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import static org.assertj.core.api.Assertions.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    public void getNextRequest(){
        //given
        Request request = new Request(Status.ROZPATRYWANY,"Prosze o zwolnienie");
        Request request2 = new Request(Status.NIEROZPATRZONY,"Prosze o zwolnienie");

        Request request3 = new Request(Status.NIEROZPATRZONY,"Prosze o zwolnienie");

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());

        calendar.add(Calendar.YEAR, -1);

        request.setDate(calendar.getTime());

        calendar.setTime(new Date());

        calendar.add(Calendar.YEAR, -1);

        request3.setDate(calendar.getTime());

        //when

        requestRepository.save(request);
        requestRepository.save(request2);
        requestRepository.save(request3);
        List<Request> nextRequests = requestRepository.getNextRequests();


        //then

        assertTrue(nextRequests.size() == 2);

        assertThat(nextRequests.toArray(new Request[0]))
                .usingElementComparatorIgnoringFields("date")
                .contains(request2, request3);

    }

}
