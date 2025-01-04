package com.salas.feedbackservice.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.testcontainers.containers.MongoDBContainer;

import static org.mockito.Mockito.mock;


@TestConfiguration
public class TestBeans {

    @Bean(initMethod = "start", destroyMethod = "stop")
    @ServiceConnection
    public MongoDBContainer mongoDBContainer() {
        return new MongoDBContainer("mongo:7");
    }

    @Bean
    @Primary
    public ReactiveJwtDecoder reactiveJwtDecoder() {
        return mock(ReactiveJwtDecoder.class);
    }

}
