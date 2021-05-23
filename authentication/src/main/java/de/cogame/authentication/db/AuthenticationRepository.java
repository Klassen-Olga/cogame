package de.cogame.authentication.db;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuthenticationRepository extends MongoRepository<Authentication, String> {
    List<Authentication> findByServiceName(String serviceName);
}
