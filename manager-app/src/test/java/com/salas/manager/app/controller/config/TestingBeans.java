package com.salas.manager.app.controller.config;

import com.salas.manager.app.client.RestClientProductRestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;

import static org.mockito.Mockito.mock;

@Configuration
public class TestingBeans {

    private static final String URL_FOR_WIRE_MOCK_CLIENT = "http://localhost:54321";

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return mock(ClientRegistrationRepository.class);
    }

    @Bean
    public OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository() {
        return mock(OAuth2AuthorizedClientRepository.class);
    }

    @Bean
    @Primary
    public RestClientProductRestService testClientProductRestService() {
        return new RestClientProductRestService(RestClient.builder()
                .baseUrl(URL_FOR_WIRE_MOCK_CLIENT)
                .build());
    }
}
