package com.example.orderservice.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors(e -> e.configurationSource(corsConfigurationSource()));
    http
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll().anyRequest()
            .authenticated()
        )
        .oauth2ResourceServer(e -> e.jwt(Customizer.withDefaults()));
    return http.build();
  }
  @Bean
  UrlBasedCorsConfigurationSource corsConfigurationSource() {
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
  @Order(Ordered.HIGHEST_PRECEDENCE)
  @Profile("!local")
  public SecurityFilterChain swaggerFilterChain(HttpSecurity http) throws Exception {
    http.securityMatcher("/v3/api-docs/**")
        .cors(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
        .httpBasic(Customizer.withDefaults())
        .authenticationManager(authentication -> {
          var userName=(String)authentication.getPrincipal();
          var password=(String)authentication.getCredentials();
          if(userName.equals("admin") && password.equals("1")){
            return new UsernamePasswordAuthenticationToken(userName, password,
                Collections.singleton(new SimpleGrantedAuthority("ADMIN")));
          }
          return authentication ;
        });
    return http.build();
  }
}
