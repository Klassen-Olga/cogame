package de.cogame.messageservice.web.userproxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * WebClient, which manages http- communication between message-service and user    -service
 */
@FeignClient(name = "user-service", url = "${user-service.ribbon.listOfServers}")
public interface UserServiceProxy {
    /**
     * Makes request to the user-service and
     * checks whether an user exists
     *
     * @return Event if exists, or throws NotFoundException
     */
    @GetMapping("/users/{userId}")
    public User existsUser(@PathVariable String userId);
}
