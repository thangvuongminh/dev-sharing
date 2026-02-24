package com.example.identityservice.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "oauth2_clients")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OAuth2Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "client_id",unique = true,length = 100,nullable = false)
    String clientId;
    @Column(name = "client_secret",nullable = false)
    String clientSecret;
    @Column(name = "client_name",length = 200,nullable = false)
    String clientName;
    @Column(name = "description",columnDefinition = "MEDIUMTEXT")
    String description;
    @Column(name = "require_authorization_consent",nullable = false)
    Boolean requireAuthorizationConsent;
    @Column(name = "require_proof_key",nullable = false)
    Boolean requireProofKey;
    @Column(name = "access_token_time_to_live")
    Integer accessTokenTimeToLive;
    @Column(name = "refresh_token_time_to_live",nullable = false)
    Integer refreshTokenTimeToLive;
    @Column(name = "is_active")
    Boolean isActive;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    User createdBy;
    @CreationTimestamp
    @Column(name = "created_at",updatable = false,nullable = false)
    LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    LocalDateTime updatedAt;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "oauth2_client_auth_methods",joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "auth_method",nullable = false)
    Set<String> authenticationMethods;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "oauth2_client_grant_types",joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "grant_type",nullable = false,length = 50)
    Set<String> grantTypes;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "oauth2_client_redirect_uris",joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "redirect_uri",nullable = false,length = 500)
    Set<String> redirectUris;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "oauth2_client_scopes",joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "scope",nullable = false,length = 100)
    Set<String> scopes;
    // Utility methods
    public void addRedirectUri(String redirectUri) {
        this.redirectUris.add(redirectUri);
    }

    public void addScope(String scope) {
        this.scopes.add(scope);
    }

    public void addGrantType(String grantType) {
        this.grantTypes.add(grantType);
    }

    public void addAuthenticationMethod(String authMethod) {
        this.authenticationMethods.add(authMethod);
    }
}
