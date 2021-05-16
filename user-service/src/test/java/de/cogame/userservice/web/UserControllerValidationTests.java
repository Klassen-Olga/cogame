package de.cogame.userservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.cogame.userservice.initializr.UserInitializr;
import de.cogame.userservice.model.User;
import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.IOException;
import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/*
 * Test class to mock UserController's post method validation
 *  without any spring context
 *
 * */
@Log4j2
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerValidationTests {

    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;

    @MockBean
    UserController userController;


    @Before
    public void before() {

        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    }

    @Test
    public void postUserWithInvalidNameWillReturn400() throws Exception {

        // when
        User user= UserInitializr.getUser("");
        user.setName("");

        //then
        testValidationForPostMethod("Name should have at least 2 characters", user);

    }

    @Test
    public void postUserWithInvalidDateOfBirthWillReturn400() throws Exception {

        // when
        User user= UserInitializr.getUser("");
        user.setDateOdBirth(LocalDate.of(2022, 1,1));

        // then
        testValidationForPostMethod("Date must be in the past", user);

    }

    @Test
    public void postUserWithInvalidEmailWillReturn400() throws Exception {

        // when
        User user= UserInitializr.getUser("");
        user.getAccount().setEmail("email");

        // then
        testValidationForPostMethod("Email should be unique and have form example@ex.com", user);

    }

    @Test
    public void postUserWithInvalidOccupationWillReturn400() throws Exception {

        // when
        User user= UserInitializr.getUser("");
        user.getOccupation().setOccupationName("");

        // then
        testValidationForPostMethod("Occupation name should be at least 5 characters e.g. 'Student', 'Worker'", user);

    }

    @Test
    public void postUserWithInvalidSexTypeWillReturn400() throws Exception {

        // when
        User user= UserInitializr.getUser("");
        user.setSex("Others");

        // then
        testValidationForPostMethod("Allowed values: MALE, FEMALE, OTHER", user);

    }

    @Test
    public void postUserWithInvalidPlaceOfLivingWillReturn400() throws Exception {

        // when
        User user= UserInitializr.getUser("");
        user.getPlaceOfLiving().setCity("");

        // then
        testValidationForPostMethod("City should contain al least 2 character", user);

    }

    void testValidationForPostMethod(String message, User user) throws Exception {
        String jsonString = objectMapper.writeValueAsString(user);


        RequestBuilder request = MockMvcRequestBuilders
                .post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON);

        // then
        this.mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(MethodArgumentNotValidException.class))
                .andExpect(content().string(containsString(message)))
                .andDo(print());

    }

}