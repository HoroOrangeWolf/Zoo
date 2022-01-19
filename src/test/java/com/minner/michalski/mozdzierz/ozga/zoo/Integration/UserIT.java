package com.minner.michalski.mozdzierz.ozga.zoo.Integration;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
public class UserIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository repository;

    @AfterEach
    public void clear(){
        repository.deleteAll();
    }

    @Test
    void registerUserTest() throws Exception {
        //given
        User user = new User("Wojciech",
                "TestPass",
                false,
                new Date(new java.util.Date().getTime()),
                "wojciech@gmail.com" );



        //when
        ResultActions resultActions = mockMvc.perform(post("http://localhost/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        //then
        resultActions.andExpect(status().isOk());
        List<User> userList = repository.findAll();

        assertThat(userList)
                .usingElementComparatorIgnoringFields("id",  "ticketHistories", "lastLogin")
                .contains(user);
    }

    @Test
    void deleteUser() throws Exception {
        //given
        User user = new User("Wojciech",
                "TestPass",
                false,
                new Date(new java.util.Date().getTime()),
                "wojciech@gmail.com" );
        repository.save(user);
        //when
        ResultActions resultActions = mockMvc.perform(
                delete("http://localhost/api/v1/users/" + user.getId()));
        //then
        List<User> userList = repository.findAll();

        assertThat(userList)
                .usingElementComparatorIgnoringFields("id",  "ticketHistories", "lastLogin")
                .doesNotContain(user);

    }
}
