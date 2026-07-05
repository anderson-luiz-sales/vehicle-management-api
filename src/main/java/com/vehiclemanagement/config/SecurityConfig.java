package com.vehiclemanagement.config;

import com.vehiclemanagement.security.CustomAccessDeniedHandler;
import com.vehiclemanagement.security.CustomAuthenticationEntryPoint;
import com.vehiclemanagement.security.CustomUserDetailsService;
import com.vehiclemanagement.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final CustomUserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;
  private final CustomAuthenticationEntryPoint authenticationEntryPoint;
  private final CustomAccessDeniedHandler accessDeniedHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(Customizer.withDefaults())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(exception -> exception
            .authenticationEntryPoint(authenticationEntryPoint)
            .accessDeniedHandler(accessDeniedHandler))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth/login", "/swagger-ui/**", "/swagger-ui.html",
                "/v3/api-docs/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/v1/vehicles", "/v1/vehicles/**")
            .hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.POST, "/v1/vehicles", "/v1/vehicles/**")
            .hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/v1/vehicles", "/v1/vehicles/**")
            .hasRole("ADMIN")
            .requestMatchers(HttpMethod.PATCH, "/v1/vehicles", "/v1/vehicles/**")
            .hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/v1/vehicles", "/v1/vehicles/**")
            .hasRole("ADMIN")
            .anyRequest().authenticated())
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder);
    return provider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }
}
