package com.salas.customerapp.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
//        return httpSecurity.
//                .authorizeExchange(customizer ->
//                        customizer
//                                .pathMatchers("/actuator/**").permitAll()
//                                .anyExchange().authenticated())
//                .csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .oauth2Login(Customizer.withDefaults())
//                .oauth2Client(Customizer.withDefaults())
//                .build();
//    }
}
