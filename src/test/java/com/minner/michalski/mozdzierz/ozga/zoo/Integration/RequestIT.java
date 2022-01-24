package com.minner.michalski.mozdzierz.ozga.zoo.Integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minner.michalski.mozdzierz.ozga.zoo.Request.Request;
import com.minner.michalski.mozdzierz.ozga.zoo.Request.RequestRepository;
import com.minner.michalski.mozdzierz.ozga.zoo.Request.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
public class RequestIT {
    @Autowired
    private MockMvc mockMvc;

    private final static String url = "http://localhost";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RequestRepository repository;

    @AfterEach
    public void clear(){
        repository.deleteAll();
    }

    @Test
    void addRequest() throws Exception {
        //given
        Request request = new Request(Status.NIEROZPATRZONY,"Prosze o zwolnienie");

        //when
        ResultActions resultActions = mockMvc.perform(post(url + "/api/v1/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk());
        List<Request> userList = repository.findAll();

        assertThat(userList)
                .usingElementComparatorIgnoringFields("id")
                .contains(request);
    }

    @Test
    void removeRequest() throws Exception {
        //given
        Request request = new Request(Status.NIEROZPATRZONY,"Prosze o zwolnienie");

        //when

        repository.save(request);

        ResultActions resultActions = mockMvc.perform(delete(url + "/api/v1/request/" + request.getId()))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk());
        List<Request> userList = repository.findAll();

        assertThat(userList)
                .usingElementComparatorIgnoringFields("id")
                .doesNotContain(request);
    }

    @Test
    void updateRequest() throws Exception {
        //given
        Request request = new Request(Status.NIEROZPATRZONY,"Prosze o zwolnienie");

        Request toUpdate = new Request(Status.NIEROZPATRZONY,"Po aktualizacji");
        //when

        repository.save(request);

        toUpdate.setId(request.getId());

        ResultActions resultActions = mockMvc.perform(put(url + "/api/v1/request/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toUpdate)));

        //then
        resultActions.andExpect(status().isOk());
        List<Request> userList = repository.findAll();

        assertThat(userList)
                .usingElementComparatorIgnoringFields("id")
                .contains(toUpdate);
    }

    @Test
    void getRequestByStatus() throws Exception {
        //given
        Request request = new Request(Status.NIEROZPATRZONY,"Prosze o zwolnienie");

        Request request2 = new Request(Status.NIEROZPATRZONY,"Prosze o zwolnienie2");
        //when

        repository.save(request);

        repository.save(request2);

        ResultActions resultActions = mockMvc.perform(get(url + "/api/v1/request/")
                .param("status", String.valueOf(Status.NIEROZPATRZONY)));

        //then
        resultActions.andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();

        Request[] requests = objectMapper.readValue(result.getResponse().getContentAsString(), Request[].class);

        assertArrayEquals(requests, new Request[]{request, request2});
    }


}
