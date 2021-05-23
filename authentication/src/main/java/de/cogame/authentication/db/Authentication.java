package de.cogame.authentication.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity class for module authentication
 * Represents a service name and service password hash pair
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Authentication {
    @Id
    private String id;

    String serviceName;
    String serviceHash;
}
