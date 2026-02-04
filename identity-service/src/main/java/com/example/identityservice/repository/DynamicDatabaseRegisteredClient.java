package com.example.identityservice.repository;

import com.example.identityservice.entity.OAuth2Client;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DynamicDatabaseRegisteredClient implements RegisteredClientRepository {
    Oauth2ClientRepository oauth2ClientRepository;
    @Override
    public void save(RegisteredClient registeredClient) {
        OAuth2Client client = convertToEntity(registeredClient);
        oauth2ClientRepository.save(client);
    }

    @Override
    public RegisteredClient findById(String id) {

        return oauth2ClientRepository.findById(Long.valueOf(id))
                .filter(OAuth2Client::getIsActive).map(this::convertToRegisteredClient).orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return oauth2ClientRepository.findByClientId(clientId)
                .filter(OAuth2Client::getIsActive).map(this::convertToRegisteredClient).orElse(null);
    }
    private RegisteredClient convertToRegisteredClient(OAuth2Client client){
        RegisteredClient.Builder registeredClient=RegisteredClient.withId(client.getId().toString())
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .clientName(client.getClientName());
        client.getRedirectUris().forEach(registeredClient::redirectUri);
        client.getScopes().forEach(registeredClient::scope);
        client.getGrantTypes().forEach(clientGrantType->{
            switch (clientGrantType.toLowerCase()) {
                case "authorization_code": registeredClient.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE); break;
                case "refresh_token": registeredClient.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN); break;
                case "client_credentials": registeredClient.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS); break;
                default: registeredClient.authorizationGrantType(new AuthorizationGrantType(clientGrantType)); break;
            }
        });
        client.getAuthenticationMethods().forEach(clientAuthenticationMethod->{
            switch (clientAuthenticationMethod.toLowerCase()) {
                case "client_secret_basic": registeredClient.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC); break;
                case "client_secret_post": registeredClient.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST); break;
                case "client_secret_jwt": registeredClient.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_JWT); break;
                case "private_key_jwt": registeredClient.clientAuthenticationMethod(ClientAuthenticationMethod.PRIVATE_KEY_JWT); break;
                default:
                    registeredClient.clientAuthenticationMethod(new ClientAuthenticationMethod(clientAuthenticationMethod));
                    break;
            }
        });
        registeredClient.clientSettings(ClientSettings.builder()
                .requireProofKey(client.getRequireProofKey())
                .requireAuthorizationConsent(client.getRequireAuthorizationConsent())
                .build());
        TokenSettings.Builder tokenSettings= TokenSettings.builder();
        if (client.getAccessTokenTimeToLive()!=null){
            tokenSettings.accessTokenTimeToLive(Duration.ofSeconds(client.getAccessTokenTimeToLive()));
        }
        if (client.getRefreshTokenTimeToLive()!=null){
            tokenSettings.refreshTokenTimeToLive(Duration.ofSeconds(client.getRefreshTokenTimeToLive()));
        }
        registeredClient.tokenSettings(tokenSettings.build());
        return registeredClient.build();
    }
    private OAuth2Client convertToEntity(RegisteredClient registeredClient) {
        OAuth2Client oAuth2Client=new OAuth2Client();
        if (registeredClient.getClientId() != null) {
            try {
                oAuth2Client.setId(Long.valueOf(registeredClient.getId()));
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        oAuth2Client.setClientId(registeredClient.getClientId());
        oAuth2Client.setClientName(registeredClient.getClientName());
        oAuth2Client.setClientSecret(registeredClient.getClientSecret());
        oAuth2Client.setRedirectUris(registeredClient.getRedirectUris());
        oAuth2Client.setScopes(registeredClient.getScopes());
        Set<String> grantTypes=registeredClient.getAuthorizationGrantTypes().stream().map(AuthorizationGrantType::getValue).collect(Collectors.toSet());
        oAuth2Client.setGrantTypes(grantTypes);
        Set<String> authenticationMethods  =registeredClient.getClientAuthenticationMethods().stream().map(ClientAuthenticationMethod::getValue).collect(Collectors.toSet());
        oAuth2Client.setAuthenticationMethods(authenticationMethods);
        oAuth2Client.setRequireProofKey(registeredClient.getClientSettings().isRequireProofKey());
        oAuth2Client.setRequireAuthorizationConsent(registeredClient.getClientSettings().isRequireAuthorizationConsent());
        oAuth2Client.setAccessTokenTimeToLive((int) registeredClient.getTokenSettings().getAccessTokenTimeToLive().getSeconds());
        oAuth2Client.setRefreshTokenTimeToLive((int) registeredClient.getTokenSettings().getRefreshTokenTimeToLive().getSeconds());
        return oAuth2Client;
    }
}