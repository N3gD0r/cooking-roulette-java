package org.n3gd0r.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui**").permitAll()
                        .requestMatchers("/authorized").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/recipes").hasAnyAuthority("SCOPE_write")
                        .requestMatchers(HttpMethod.POST, "/api/recipes/search").hasAnyAuthority("SCOPE_read")
                        .requestMatchers(HttpMethod.GET, "/api/recipes/**").hasAnyAuthority("SCOPE_read")
                        .requestMatchers(HttpMethod.GET, "/api/recipes**").hasAnyAuthority("SCOPE_read")
                        .requestMatchers(HttpMethod.PUT, "/api/recipes/**").hasAnyAuthority("SCOPE_write")
                        .requestMatchers(HttpMethod.PATCH, "/api/recipes/**").hasAnyAuthority("SCOPE_write")
                        .requestMatchers(HttpMethod.DELETE, "/api/recipes/**").hasAnyAuthority("SCOPE_write")
                        .anyRequest().authenticated())
                .oauth2Login(login -> login.loginPage("/oauth2/authorization/cooking-auth"))
                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation(issuerUri);
    }
}
