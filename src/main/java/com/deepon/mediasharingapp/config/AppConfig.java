package com.deepon.mediasharingapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppConfig {

        @Bean
        public SecurityFilterChain securityConfiguration(HttpSecurity http) throws Exception {
            http
                    .sessionManagement(sessionManagement ->
                            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    .authorizeHttpRequests(authorize ->
                            authorize
                                    .requestMatchers(HttpMethod.POST, "/signup").permitAll()
                                    .anyRequest().authenticated()
                    )
                    .addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                    .addFilterBefore(new JwtTokenValidationFilter(), BasicAuthenticationFilter.class)
                    .csrf(csrf -> csrf.disable())
                    .formLogin(Customizer.withDefaults())
                    .httpBasic(Customizer.withDefaults());


            return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

}
/*
 http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeHttpRequests().requestMatchers(HttpMethod.POST,"/signup").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                    .addFilterBefore(new JwtTokenValidationFilter(),BasicAuthenticationFilter.class)
                    .csrf().disable()
                    .formLogin().and().httpBasic();
 */