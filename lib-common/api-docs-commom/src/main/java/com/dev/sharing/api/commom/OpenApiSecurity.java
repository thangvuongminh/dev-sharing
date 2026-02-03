package com.dev.sharing.api.commom;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@RequiredArgsConstructor
public class OpenApiSecurity {
  private final  OpenApiSecurityWithData openApiSecurityWithData;
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
          if(userName.equals(openApiSecurityWithData.getUsername()) && password.equals(openApiSecurityWithData.getPassword())){
            return new UsernamePasswordAuthenticationToken(userName, password,
                Collections.singleton(new SimpleGrantedAuthority("ADMIN")));
          }
          throw new BadCredentialsException("Invalid username or password");

        });
    return http.build();
  }
}
