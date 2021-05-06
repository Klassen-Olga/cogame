package de.cogame.userservice.web;

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
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
//no ApplicationContext will be loaded
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

        given(this.userController.greeting1()).willReturn("Hello from user-service");

        this.mvc.perform(get("/greeting"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from user-service"));

    }


    @Test
    public void getUsersShouldReturnUsers() throws Exception {
        User user = UserInitializr.getUser("1");

        given(this.userController.getUsers()).willReturn(Collections.singletonList(user));

        String users = StreamUtils.copyToString(usersFile.getInputStream(), Charset.defaultCharset());

        this.mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(users));

    }

    @Test
    public void getUser1ShouldReturnUsers1() throws Exception {
        User user = UserInitializr.getUser("1");

        given(this.userController.getUser("1")).willReturn(user);

        String users = StreamUtils.copyToString(userFile.getInputStream(), Charset.defaultCharset());

        this.mvc.perform(get("/users/1").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(users));

    }

    @Test
    public void get2UsersWithId1And2() throws Exception {
        List<String> usersId = new ArrayList<>();
        usersId.add("1");
        usersId.add("2rfdsa");

        List<User> users = new ArrayList<>();
        users.add(UserInitializr.getUser("1"));


        given(this.userController.getCertainUsers(usersId)).willReturn(users);

        String usersFromFile = StreamUtils.copyToString(usersFile.getInputStream(), Charset.defaultCharset());

        RequestBuilder request = MockMvcRequestBuilders
                .get("/certain-users")
                .accept(MediaType.APPLICATION_JSON)
                .content("[\"1\", \"2rfdsa\"]")
                .contentType(MediaType.APPLICATION_JSON);


        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(usersFromFile));
    }
    // for void functions
/*
*
* given(this.userController.createUser(UserInitializr.getUser("1"))).willAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                URI location= ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .buildAndExpand("1")
                        .toUri();
                return ResponseEntity.created(location).build();
            }
        });
*
* */

    @Test
    public void postUserAndGetCreatedStatusCode() throws Exception {
        ResponseEntity<Object> r=new ResponseEntity<>("", HttpStatus.CREATED);

        when(this.userController.createUser(UserInitializr.getUser("1"))).thenReturn(new ResponseEntity(HttpStatus.CREATED));

        String userFromFile = StreamUtils.copyToString(userFile.getInputStream(), Charset.defaultCharset());

        RequestBuilder request = MockMvcRequestBuilders
                .post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(userFromFile)
                .contentType(MediaType.APPLICATION_JSON);



        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(userFromFile));

    }
}