package com.github.gcnyin.mybatisdemo.config.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BearerAuthenticationFilter extends OncePerRequestFilter {
  private final AuthenticationManager authenticationManager;
  private final AuthenticationEntryPoint authenticationEntryPoint = new Http403ForbiddenEntryPoint();
  private final BearerTokenAuthenticationConverter authenticationConverter;

  public BearerAuthenticationFilter(AuthenticationManager authenticationManager, BearerTokenAuthenticationConverter authenticationConverter) {
    this.authenticationManager = authenticationManager;
    this.authenticationConverter = authenticationConverter;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    BearerAuthenticationToken authRequest = authenticationConverter.convert(request);
    if (authRequest == null) {
      filterChain.doFilter(request, response);
      return;
    }
    try {
      Authentication authResult = authenticationManager.authenticate(authRequest);
      SecurityContextHolder.getContext().setAuthentication(authResult);
    } catch (AuthenticationException e) {
      SecurityContextHolder.clearContext();
      authenticationEntryPoint.commence(request, response, e);
      return;
    }
    filterChain.doFilter(request, response);
  }
}
