package com.minner.michalski.mozdzierz.ozga.zoo.IntegrationIT;

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
import org.springframework.web.util.NestedServletException;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
public class UserIT {

    @Autowired
    private MockMvc mockMvc;

    private final static String url = "http://localhost";

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
        ResultActions resultActions = mockMvc.perform(post(url + "/api/v1/users")
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
    void registerUserWithExistingEmailTest() throws Exception {
        //given
        User user = new User("Wojciech",
                "TestPass",
                false,
                new Date(new java.util.Date().getTime()),
                "wojciech@gmail.com" );

        User user2 = new User("Bartek",
                "TestPass",
                false,
                new Date(new java.util.Date().getTime()),
                "wojciech@gmail.com" );

        //when

        repository.save(user2);

        assertThatThrownBy(()->{
            try{
                ResultActions resultActions = mockMvc.perform(post(url + "/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(user)))
                        .andDo(print());
            }catch (NestedServletException e){
                throw e.getCause();
            }

        }).isInstanceOf(IllegalStateException.class).hasMessage("User identified by this email exists in database!");

        //then

    }



    @Test
    void registerUserWithInvalidEmailAssertException() throws Exception {
        //given
        User user = new User("Wojciech",
                "TestPass",
                false,
                new Date(new java.util.Date().getTime()),
                "ivalidemail.com" );

        //when
        ResultActions result= mockMvc.perform(post(url + "/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        //then
        result.andExpect(status().isBadRequest());
    }

    @Test
    void registerUserWithInvalidPasswordAssertException() throws Exception {
        //given
        User user = new User("Wojciech",
                "T",
                false,
                new Date(new java.util.Date().getTime()),
                "ivalidemail.com" );

        //when
        ResultActions resultActions = mockMvc.perform(post(url + "/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void registerUserWithInvalidNickAssertException() throws Exception {
        //given
        User user = new User("Wo",
                "Tomas",
                false,
                new Date(new java.util.Date().getTime()),
                "ivalidemail.com" );

        //when
        ResultActions resultActions = mockMvc.perform(post(url + "/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        //then
        resultActions.andExpect(status().isBadRequest());
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
                delete(url + "/api/v1/users/" + user.getId()));
        //then
        List<User> userList = repository.findAll();

        assertThat(userList)
                .usingElementComparatorIgnoringFields("id",  "ticketHistories", "lastLogin")
                .doesNotContain(user);

    }

    @Test
    void changeEmail() throws Exception {
        //given
        User user = new User("Wojciech",
                "TestPass",
                false,
                new Date(new java.util.Date().getTime()),
                "wojciech@gmail.com" );
        repository.save(user);
        //when

        String email = "jacek@gmail.com";

        mockMvc.perform(put(url + "/api/v1/users/" + user.getId() + "/changeEmail")
                .contentType(MediaType.APPLICATION_JSON)
                .content(email)
        ).andDo(print());

        //then
        Optional<User> byId = repository.findById(user.getId());

        assertTrue(byId.isPresent());

        assertEquals(byId.get().getEmail(), "jacek@gmail.com");
    }

    @Test
    void changeEmailWithInvalidEmail()  {
        //given
        User user = new User("Wojciech",
                "TestPass",
                false,
                new Date(new java.util.Date().getTime()),
                "wojciech@gmail.com" );
        repository.save(user);
        //when
        String email = "invalidEmail.com";

        assertThatThrownBy(()->{
            try{
                mockMvc.perform(put(url + "/api/v1/users/" + user.getId() + "/changeEmail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(email));
            }catch (NestedServletException e){
                throw e.getCause().getCause().getCause();
            }

        }).isInstanceOf(javax.validation.ConstraintViolationException.class);
    }

    @Test
    void changePassword() throws Exception {
        //given
        User user = new User("Wojciech",
                "TestPass",
                false,
                new Date(new java.util.Date().getTime()),
                "wojciech@gmail.com" );
        repository.save(user);
        //when

        String password = "newPassword";

        mockMvc.perform(put(url + "/api/v1/users/" + user.getId() + "/changePassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(password)
        ).andDo(print());

        //then
        Optional<User> byId = repository.findById(user.getId());

        assertTrue(byId.isPresent());

        assertEquals(byId.get().getPassword(), password);
    }

    @Test
    void updateUser() throws Exception {
        //given
        User user = new User("Wojciech",
                "TestPass",
                false,
                new Date(new java.util.Date().getTime()),
                "wojciech@gmail.com" );

        User toBeUpdated = new User("Test",
                "TestPass",
                false,
                new Date(new java.util.Date().getTime()),
                "jacus@gmail.com" );

        //when
        repository.save(user);

        toBeUpdated.setId(user.getId());

        mockMvc.perform(put(url + "/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toBeUpdated))
        ).andDo(print());

        //then
        Optional<User> byId = repository.findById(user.getId());

        assertTrue(byId.isPresent());

        assertEquals(byId.get(), toBeUpdated);
    }


    @Test
    void updateUserInvalidValid() throws Exception {
        //given
        User user = new User("Wojciech",
                "TestPass",
                false,
                new Date(new java.util.Date().getTime()),
                "wojciech@gmail.com" );

        User toBeUpdated = new User("Test",
                "TestPass",
                false,
                new Date(new java.util.Date().getTime()),
                "invalid com" );

        //when
        repository.save(user);

        user.setEmail("Invalid");

        //then
        mockMvc.perform(put(url + "/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
        ).andDo(print()).andExpect(status().isBadRequest());

    }

    @Test
    void changePasswordWithInvalidPassword()  {
        //given
        User user = new User("Wojciech",
                "TestPass",
                false,
                new Date(new java.util.Date().getTime()),
                "wojciech@gmail.com" );

        repository.save(user);
        //when
        String password = "Ps";

        assertThatThrownBy(()->{
            try{
                mockMvc.perform(put(url + "/api/v1/users/" + user.getId() + "/changePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(password));
            }catch (NestedServletException e){
                throw e.getCause().getCause().getCause();
            }

        }).isInstanceOf(javax.validation.ConstraintViolationException.class);
    }
}
