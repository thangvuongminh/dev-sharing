package com.example.identityservice.repository;

import com.example.identityservice.entity.OAuth2Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Oauth2ClientRepository extends JpaRepository<OAuth2Client,Long> {
    Optional<OAuth2Client> findByClientId(String clientId);
}
