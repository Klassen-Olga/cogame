package de.cogame.userservice.web;

import de.cogame.userservice.initializr.UserInitializr;
import de.cogame.userservice.model.User;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
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
        User user= UserInitializr.getUser("1");

        given(this.userController.getUsers()).willReturn(Collections.singletonList(user));

        String users = StreamUtils.copyToString( usersFile.getInputStream(), Charset.defaultCharset());

        this.mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json( users));

    }
    @Test
    public void getUser1ShouldReturnUsers1() throws Exception {
        User user= UserInitializr.getUser("1");

        given(this.userController.getUser("1")).willReturn(user);

        String users = StreamUtils.copyToString( userFile.getInputStream(), Charset.defaultCharset());

        this.mvc.perform(get("/users/1").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json( users));

    }
}