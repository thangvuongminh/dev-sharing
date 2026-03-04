package com.example.orderservice.util;

import java.util.Map;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class UserContextExtractor{
  public static Long  getUserId(){
    JwtAuthenticationToken authentication = (JwtAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
    Map<String,Object> claims= authentication.getToken().getClaims();
    return (Long)claims.get("id");
  }

}
