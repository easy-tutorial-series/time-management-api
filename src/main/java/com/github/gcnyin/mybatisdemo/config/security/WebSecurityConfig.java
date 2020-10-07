package com.github.gcnyin.mybatisdemo.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
      .antMatchers(HttpMethod.GET, "/heartbeat")
      .antMatchers(HttpMethod.POST, "/user")
      .antMatchers(HttpMethod.GET, "/swagger-ui.html**")
      .antMatchers(HttpMethod.GET, "/webjars/springfox-swagger-ui/**")
      .antMatchers(HttpMethod.GET, "/swagger-resources/**")
      .antMatchers(HttpMethod.GET, "/v2/api-docs");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
      .antMatchers(HttpMethod.GET, "/heartbeat").permitAll()
      .antMatchers(HttpMethod.POST, "/user").permitAll()
      .antMatchers(HttpMethod.GET, "/swagger-ui.html**").permitAll()
      .antMatchers(HttpMethod.GET, "/webjars/springfox-swagger-ui/**").permitAll()
      .antMatchers(HttpMethod.GET, "/swagger-resources/**").permitAll()
      .antMatchers(HttpMethod.GET, "/v2/api-docs").permitAll();
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }
}
