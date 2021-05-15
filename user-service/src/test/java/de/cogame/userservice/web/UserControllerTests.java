package de.cogame.userservice.web;

import de.cogame.globalhandler.exception.NotFoundException;
import de.cogame.userservice.initializr.UserInitializr;
import de.cogame.userservice.model.User;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StreamUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
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
    UserController userController;

    @Value("classpath:data/users.json")
    Resource usersFile;

    @Value("classpath:data/user.json")
    Resource userFile;


    @Test
    public void returnsHelloFromUserService() throws Exception {

        // when
        given(this.userController.greeting1()).willReturn("Hello from user-service");

        // then
        this.mvc.perform(get("/greeting"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from user-service"));

    }


    @Test
    public void getUsersShouldReturnUsers() throws Exception {
        User user = UserInitializr.getUser("1");
        String users = StreamUtils.copyToString(usersFile.getInputStream(), Charset.defaultCharset());

        //when
        given(this.userController.getUsers()).willReturn(Collections.singletonList(user));


        //then
        this.mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(users));

    }

    @Test
    public void getNotExistingUserWillThrowNotFoundException() throws Exception {

        // given
        doThrow(NotFoundException.class).when(userController).getUser("ulala");

        // then
        this.mvc.perform(get("/users/ulala").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void getUser1ShouldReturnUsers1() throws Exception {
        User user = UserInitializr.getUser("1");
        String users = StreamUtils.copyToString(userFile.getInputStream(), Charset.defaultCharset());

        // when
        given(this.userController.getUser("1")).willReturn(user);

        //then
        this.mvc.perform(get("/users/1").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(users));

    }

    @Test
    public void getCertainUserShouldReturnUserWithId1() throws Exception {
        String usersFromFile = StreamUtils.copyToString(usersFile.getInputStream(), Charset.defaultCharset());
        List<String> usersId = new ArrayList<>();
        usersId.add("1");
        usersId.add("2rfdsa");

        List<User> users = new ArrayList<>();
        users.add(UserInitializr.getUser("1"));

        // when
        given(this.userController.getCertainUsers(usersId)).willReturn(users);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/certain-users")
                .accept(MediaType.APPLICATION_JSON)
                .content("[\"1\", \"2rfdsa\"]")
                .contentType(MediaType.APPLICATION_JSON);
        //then
        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(usersFromFile));
    }

    @Test
    public void postUserAndGetCreatedStatusCode() throws Exception {

        // given
        String userFromFile = StreamUtils.copyToString(userFile.getInputStream(), Charset.defaultCharset());
        ResponseEntity<Object> r = new ResponseEntity<>(HttpStatus.CREATED);
        doReturn(r).when(userController).createUser(any());

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
    public void postUserWithSameEmailAndGetConflictStatusCode() throws Exception {

        // given
        String userFromFile = StreamUtils.copyToString(userFile.getInputStream(), Charset.defaultCharset());
        ResponseEntity<Object> r = new ResponseEntity<>(HttpStatus.CONFLICT);
        doReturn(r).when(userController).createUser(UserInitializr.getUser("1"));

        // when
        RequestBuilder request = MockMvcRequestBuilders
                .post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(userFromFile)
                .contentType(MediaType.APPLICATION_JSON);

        // then
        this.mvc.perform(request)
                .andExpect(status().isConflict())
                .andDo(print());

    }

    @Test
    public void deleteExistingUserWillReturnOk() throws Exception {

        // given
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                return null;
            }
        }).when(userController).deleteUser("1");

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

        // given
        doThrow(NotFoundException.class).when(userController).deleteUser("blabla");

        // when
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/users/blabla")
                .accept(MediaType.APPLICATION_JSON);

        // then
        this.mvc.perform(request)
                .andExpect(status().isNotFound())
                .andDo(print());

    }

}