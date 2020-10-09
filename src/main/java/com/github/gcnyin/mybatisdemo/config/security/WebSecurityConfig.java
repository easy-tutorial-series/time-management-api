package com.github.gcnyin.mybatisdemo.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  private final BearerTokenAuthenticationConverter bearerTokenAuthenticationConverter;
  private final BearerAuthenticationProvider bearerAuthenticationProvider;
  private final UserDetailsService userDetailsService;

  public WebSecurityConfig(BearerTokenAuthenticationConverter bearerTokenAuthenticationConverter, BearerAuthenticationProvider bearerAuthenticationProvider, UserDetailsService userDetailsService) {
    this.bearerTokenAuthenticationConverter = bearerTokenAuthenticationConverter;
    this.bearerAuthenticationProvider = bearerAuthenticationProvider;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    daoAuthenticationProvider.setPasswordEncoder(encoder());
    auth.authenticationProvider(bearerAuthenticationProvider)
      .authenticationProvider(daoAuthenticationProvider);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
      .antMatchers(HttpMethod.GET, "/swagger-ui.html**")
      .antMatchers(HttpMethod.GET, "/webjars/springfox-swagger-ui/**")
      .antMatchers(HttpMethod.GET, "/swagger-resources/**")
      .antMatchers(HttpMethod.GET, "/v2/api-docs")
      .antMatchers(HttpMethod.POST, "/user");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
      .antMatchers("/heartbeat").permitAll()
      .anyRequest().authenticated()
      .and().httpBasic()
      .and().addFilterBefore(bearerAuthenticationFilter(), BasicAuthenticationFilter.class)
      .formLogin().disable()
      .csrf().disable();
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public BearerAuthenticationFilter bearerAuthenticationFilter() throws Exception {
    return new BearerAuthenticationFilter(authenticationManager(), bearerTokenAuthenticationConverter);
  }
}
