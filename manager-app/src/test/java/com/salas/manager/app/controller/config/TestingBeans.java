package com.salas.manager.app.controller.config;

import com.salas.manager.app.client.RestClientProductRestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

@Configuration
public class TestingBeans {

    private static final String URL_FOR_WIRE_MOCK_CLIENT = "http://localhost:54321";


    @Bean
    @Primary
    public RestClientProductRestService testClientProductRestService() {
        return new RestClientProductRestService(RestClient.builder()
                .baseUrl(URL_FOR_WIRE_MOCK_CLIENT)
                .build());
    }
}
