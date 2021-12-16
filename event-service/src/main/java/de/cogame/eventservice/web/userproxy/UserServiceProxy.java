package de.cogame.eventservice.web.userproxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * WebClient, which manages http- communication between user-service and event-service
 */
@FeignClient(name = "user-service", url = "${user-service.ribbon.listOfServers}")
public interface UserServiceProxy {
    /**
     * Makes request to the user-service to check whether the user exists or not
     *
     * @return User if exists, or throws not found error
     */
    @GetMapping("/users/{userId}")
    User existsUser(@PathVariable String userId);
}
