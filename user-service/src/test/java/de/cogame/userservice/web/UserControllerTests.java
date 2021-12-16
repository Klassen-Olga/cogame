package de.cogame.userservice.web;

import de.cogame.globalhandler.exception.NotFoundException;
import de.cogame.userservice.initializr.UserInitializr;
import de.cogame.userservice.model.User;
import de.cogame.userservice.repository.UserRepository;
import de.cogame.userservice.service.UserService;
import de.cogame.userservice.web.eventproxy.EventServiceProxy;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StreamUtils;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * Test class to mock UserController's methods
 *  without any spring context
 *
 * */
@Log4j2
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTests {


    @Autowired
    private MockMvc mvc;

    @MockBean
    UserService userService;
    @MockBean
    EventServiceProxy eventServiceProxy;
    @MockBean
    PasswordEncoder passwordEncoder;

    @Value("classpath:data/users.json")
    Resource usersFile;

    @Value("classpath:data/user.json")
    Resource userFile;


    @Test
    public void getUsersShouldReturnUsers() throws Exception {
        User user = UserInitializr.getUser("1", "albert");
        String users = StreamUtils.copyToString(usersFile.getInputStream(), Charset.defaultCharset());

        //when
        given(this.userService.getAll()).willReturn(Collections.singletonList(user));


        //then
        this.mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(users));

    }
    @Test
    public void getNotExistingUserWillThrowNotFoundException() throws Exception {

        // given
        doThrow(NotFoundException.class).when(userService).getUser("ulala");

        // then
        this.mvc.perform(get("/users/ulala").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

    }
    @Test
    public void getUser1ShouldReturnUsers1() throws Exception {
        User user = UserInitializr.getUser("1", "albert");
        String users = StreamUtils.copyToString(userFile.getInputStream(), Charset.defaultCharset());

        // when
        given(this.userService.getUser("1")).willReturn(user);

        //then
        this.mvc.perform(get("/users/1").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(users));

    }


    @Test
    public void postUserAndGetCreatedStatusCode() throws Exception {

        // given
        String userFromFile = StreamUtils.copyToString(userFile.getInputStream(), Charset.defaultCharset());
        ResponseEntity<Object> r = new ResponseEntity<>(HttpStatus.CREATED);
        User user = UserInitializr.getUser("1", "albert");

        doReturn(user).when(userService).save(any());

        // when
        RequestBuilder request = MockMvcRequestBuilders
                .post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(userFromFile)
                .contentType(MediaType.APPLICATION_JSON);

        // then
        this.mvc.perform(request)
                .andExpect(status().isCreated())
                .andDo(print());

    }

    @Test
    public void deleteExistingUserWillReturnOk() throws Exception {
        User user = UserInitializr.getUser("1", "Albert");
        given(userService.getUser("1")).willReturn(user);
        given(userService.areEventsInFuture(new LinkedList<LocalDateTime>())).willReturn(false);
        // when
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/users/1")
                .accept(MediaType.APPLICATION_JSON);

        // then
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());

    }
    @Test
    public void deleteNotExistingUserWillThrowNotFoundException() throws Exception {

        doThrow(NotFoundException.class).when(userService).getUser("blabla");

        // when
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/users/blabla")
                .accept(MediaType.APPLICATION_JSON);

        // then
        this.mvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));

    }

}