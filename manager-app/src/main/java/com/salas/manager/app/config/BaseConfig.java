package com.salas.manager.app.config;

import com.salas.manager.app.client.RestClientProductRestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class BaseConfig {


    @Bean
    public RestClientProductRestService clientProductRestService(
            @Value("${services.catalogue.uri:http://localhost:8081}") String uri) {
        return new RestClientProductRestService(RestClient.builder()
                .baseUrl(uri)
                .build());
    }
}
