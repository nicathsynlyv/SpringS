package com.example.SpringS.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;


import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
//
//    private final AuthFilter authFilter;
//    private final StaticKeyAuthenticationFilter staticKeyAuthenticationFilter;
//    private final LoggingFilter loggingFilter;
//    private final CsrfTokenRepository csrfTokenRepository;

//    public SecurityConfig(AuthFilter authFilter, StaticKeyAuthenticationFilter staticKeyAuthenticationFilter,
//                          LoggingFilter loggingFilter, CsrfTokenRepository csrfTokenRepository) {
//        this.authFilter = authFilter;
//        this.staticKeyAuthenticationFilter = staticKeyAuthenticationFilter;
//        this.loggingFilter = loggingFilter;
//        this.csrfTokenRepository = csrfTokenRepository;
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests
                        (auth->auth.anyRequest().permitAll());
//                .csrf().and()
//        http
//                .csrf(csrf -> csrf
//                        .csrfTokenRepository(csrfTokenRepository)
//                        .ignoringRequestMatchers("/ciao")
//                )
//
//                .authorizeHttpRequests(auth -> auth
////                        .requestMatchers("/users/findByUsername").hasRole("ADMIN")
////                .requestMatchers("/users/getAll").hasRole("USER")
//                                .anyRequest().authenticated()
//                )
//        http
//                .csrf(csrf -> csrf.disable()) // CSRF qorunması deaktiv edilir
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().authenticated()
//                )
//                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(loggingFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(staticKeyAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .httpBasic()
//                .and()
//                .formLogin(withDefaults())
//                .logout(logout -> logout.logoutSuccessUrl("/login?logout"))
//                .sessionManagement(session -> session.sessionCreationPolicy(
//                        SessionCreationPolicy.IF_REQUIRED));
        return http.build();
    }

        @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }


//    @Bean
//    public CsrfTokenRepository customTokenRepository() {
//        return new CustomCsrfTokenRepository();
//    }

}


