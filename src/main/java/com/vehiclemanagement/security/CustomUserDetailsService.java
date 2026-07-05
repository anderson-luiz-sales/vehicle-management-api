package com.vehiclemanagement.security;

import com.vehiclemanagement.entity.Role;
import com.vehiclemanagement.entity.User;
import com.vehiclemanagement.repository.UserRepository;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) {
    User user = userRepository.findByEmailAndEnabledTrue(email)
        .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));

    return org.springframework.security.core.userdetails.User.builder()
        .username(user.getEmail())
        .password(user.getPassword())
        .disabled(Boolean.FALSE.equals(user.getEnabled()))
        .authorities(mapAuthorities(user))
        .build();
  }

  private Collection<? extends GrantedAuthority> mapAuthorities(User user) {
    return user.getRoles().stream()
        .map(Role::getName)
        .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
        .map(SimpleGrantedAuthority::new)
        .toList();
  }
}
