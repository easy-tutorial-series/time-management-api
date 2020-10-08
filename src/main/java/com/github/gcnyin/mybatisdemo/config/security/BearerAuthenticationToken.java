package com.github.gcnyin.mybatisdemo.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class BearerAuthenticationToken extends AbstractAuthenticationToken {
  private final String principal;
  private final String credential;

  public BearerAuthenticationToken(String principal, String credential, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    this.credential = credential;
    setAuthenticated(true);
  }

  public BearerAuthenticationToken(String token) {
    super(null);
    this.principal = token;
    this.credential = null;
  }

  @Override
  public Object getCredentials() {
    return credential;
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }
}
