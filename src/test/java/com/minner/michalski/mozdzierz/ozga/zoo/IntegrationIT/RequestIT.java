package com.minner.michalski.mozdzierz.ozga.zoo.IntegrationIT;

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
import org.springframework.web.util.NestedServletException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
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
                .usingElementComparatorIgnoringFields("id", "date")
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
                .usingElementComparatorIgnoringFields("id", "date")
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
                .usingElementComparatorIgnoringFields("id", "date")
                .contains(toUpdate);
    }

    @Test
    void getNextRequest() throws Exception {
        //given
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

        repository.save(request);
        repository.save(request2);
        repository.save(request3);
        //when

        ResultActions resultActions = mockMvc.perform(get(url + "/api/v1/request/"));

        //then
        resultActions.andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();

        Request request_returned = objectMapper.readValue(result.getResponse().getContentAsString(), Request.class);

        assertSame(request3.getId(), request_returned.getId());

        assertSame(request_returned.getStatus(), Status.ROZPATRYWANY);
    }

    @Test
    void getNextRequestNoMoreRequests() throws Exception {
        //given

        //when
        assertThatThrownBy(()->{
            try{
                ResultActions resultActions = mockMvc.perform(get(url + "/api/v1/request/"));
            }catch (NestedServletException exception){
                throw exception.getCause();
            }
        }).isInstanceOf(IllegalStateException.class).hasMessage("There is no more requests!");
        //then
    }

    @Test
    void getRequestById() throws Exception {
        //given
        Request request = new Request(Status.NIEROZPATRZONY,"Prosze o zwolnienie");

        repository.save(request);
        //when
        ResultActions resultActions = mockMvc.perform(get(url + "/api/v1/request").param("requestId", request.getId().toString()));

        //then
        resultActions.andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();

        Request reuturned = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Request.class);

        assertSame(request.getId(), reuturned.getId());

    }

    @Test
    void getRequestByInvalidId() throws Exception {
        //given
        Long invalidID = -1L;
        //when

        assertThatThrownBy(()->{
            try{
                ResultActions resultActions = mockMvc.perform(get(url + "/api/v1/request/" + invalidID));
            }catch (NestedServletException exception){
                throw exception.getCause();
            }
        }).isInstanceOf(IllegalStateException.class).hasMessage("Invalid id");



        //then


    }


}
