package com.salas.manager.app.client;

import org.springframework.web.client.HttpClientErrorException;

public class CustomException extends Throwable {
    public CustomException(String string, HttpClientErrorException.BadRequest exception) {
        super(string, exception);
    }
}
