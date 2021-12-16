package de.cogame.eventservice.web.userproxy;

import de.cogame.eventservice.web.messageproxy.Message;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "user-service", url = "${user-service.ribbon.listOfServers}")
public interface UserServiceProxy {
    @GetMapping("/users/{userId}")
    User existsUser(@PathVariable String userId);
}
