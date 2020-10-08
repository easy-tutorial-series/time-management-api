package com.github.gcnyin.mybatisdemo.config.security;

import com.github.gcnyin.mybatisdemo.mapper.UserMapper;
import com.github.gcnyin.mybatisdemo.model.Authority;
import com.github.gcnyin.mybatisdemo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserMapper userMapper;

  public UserDetailsServiceImpl(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userMapper.findByName(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    List<SimpleGrantedAuthority> authorities = user.getAuthorities().stream()
      .map(Authority::getAuthority)
      .map(SimpleGrantedAuthority::new)
      .collect(Collectors.toList());
    return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
  }
}
