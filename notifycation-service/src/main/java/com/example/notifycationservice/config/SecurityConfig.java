package com.example.notifycationservice.config;

import com.example.notifycationservice.config.properties.NotificationCredentialsProperties;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityConfig {
    NotificationCredentialsProperties notificationCredentialsProperties;

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(corsConfigurer->corsConfigurer.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.requestMatchers("swagger-ui/**", "v3/api-docs/**").permitAll()
                                .anyRequest().authenticated());
        http.oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(Customizer.withDefaults()));
        return http.build();
    }

        public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChainBasicAuth(HttpSecurity http) throws Exception {
        http.securityMatcher("/internal/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .authenticationManager(authentication -> {
                            String username = authentication.getPrincipal().toString();
                            String password = authentication.getCredentials().toString();
                            if(notificationCredentialsProperties.getUsername().equals(username) && notificationCredentialsProperties.getPassword().equals(password)) {
                                return new UsernamePasswordAuthenticationToken(username, password, Collections.singleton(new SimpleGrantedAuthority("service-internal")));
                            }
                            return authentication;
                        }
                );
        return http.build();
    }
}
