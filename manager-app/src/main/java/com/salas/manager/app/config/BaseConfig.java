package com.salas.manager.app.config;

import com.salas.manager.app.OAuthClientRequestInterceptor;
import com.salas.manager.app.client.RestClientProductRestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;

@Configuration
public class BaseConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public RestClientProductRestService clientProductRestService(
            @Value("${services.catalogue.uri:http://localhost:8081}") String uri,
            @Value("${services.catalogue.registration-id:keykloak}") String registrationId,
            ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientRepository authorizedClientRepository) {
        return new RestClientProductRestService(RestClient.builder()
                .baseUrl(uri)
                .requestInterceptor(
                        new OAuthClientRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }
}
