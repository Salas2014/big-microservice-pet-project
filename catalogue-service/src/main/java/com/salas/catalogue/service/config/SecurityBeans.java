package com.salas.catalogue.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityBeans {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry
                            .requestMatchers(HttpMethod.POST).hasAuthority("SCOPE_edit_catalogue")
                            .requestMatchers(HttpMethod.PATCH, "/catalogue/products/{productid:\\d+}").hasAuthority("SCOPE_edit_catalogue")
                            .requestMatchers(HttpMethod.DELETE, "/catalogue/products/{productid:\\d+}").hasAuthority("SCOPE_edit_catalogue")
                            .requestMatchers(HttpMethod.GET).hasAuthority("SCOPE_view_catalogue")
                            .anyRequest().denyAll();
                })
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer -> {
                    httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer -> {
                    httpSecurityOAuth2ResourceServerConfigurer.jwt(Customizer.withDefaults());
                })
                .build();
    }
}


