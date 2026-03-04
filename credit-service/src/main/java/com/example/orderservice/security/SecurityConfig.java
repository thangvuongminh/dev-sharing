package com.example.orderservice.security;

import com.example.orderservice.security.properties.BasicCredentialsProperties;
import java.util.Arrays;
import java.util.Collections;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SecurityConfig {
  BasicCredentialsProperties basicCredentialsProperties;
  @Bean
  @Order(1)
  public SecurityFilterChain filterChainBasicAuth(HttpSecurity http) throws Exception {
    http.cors(e -> e.configurationSource(corsConfigurationSource()));
    http
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll().anyRequest()
            .authenticated()
        ).securityMatcher(request -> {
           String header = request.getHeader("Authorization");
           return header != null && header.startsWith("Bearer ");
        }).httpBasic(Customizer.withDefaults())
        .authenticationManager(authentication ->  {
          String username =(String) authentication.getPrincipal();
          String password = (String)authentication.getCredentials();
          if(basicCredentialsProperties.getUsername().equals(username) && basicCredentialsProperties.getPassword().equals(password)) {
            return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
          }
          return authentication;
        })
        .oauth2ResourceServer(e -> e.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
    return http.build();
  }
  @Bean
  @Order(2)
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors(e -> e.configurationSource(corsConfigurationSource()));
    http
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll().anyRequest()
            .authenticated()
        )
        .oauth2ResourceServer(e -> e.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
    return http.build();
  }
  @Bean
  public UrlBasedCorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
    configuration.setAllowedMethods(Arrays.asList("*"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
  @Bean
  public JwtAuthenticationConverter  jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
    jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
    return jwtAuthenticationConverter;

  }


}
