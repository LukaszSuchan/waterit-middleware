package agh.iot.waterit.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final AccountAuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        http.csrf().disable();
        http.authorizeHttpRequests()
                .requestMatchers(POST, "/account/**")
                .permitAll()
                .requestMatchers(POST, "/device/history/**")
                .permitAll()
                .requestMatchers(DELETE, "/device/esp/**")
                .permitAll()
                .requestMatchers(POST, "/device/esp/**")
                .permitAll()
                .requestMatchers(GET, "/device/esp/**")
                .permitAll()
                .requestMatchers("/**")
                .authenticated()
                .anyRequest()
                .hasAnyRole("USER")
                .and()
                .authenticationProvider(authenticationProvider)
                .httpBasic(withDefaults())
                .sessionManagement()
                .sessionCreationPolicy(STATELESS);
        http.cors();
        return http.build();
    }
}
