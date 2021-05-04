package de.cogame.userservice.repository;

import de.cogame.userservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserRepository extends MongoRepository<User, String> {
     List<User> getAllByIdIn(List<String> ids);
}
