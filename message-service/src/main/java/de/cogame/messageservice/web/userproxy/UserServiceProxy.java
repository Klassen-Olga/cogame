package de.cogame.messageservice.web.userproxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${user-service.ribbon.listOfServers}")
public interface UserServiceProxy {
    @GetMapping("/users/{userId}")
    public User existsUser(@PathVariable String userId);
}
