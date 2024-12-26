package com.salas.manager.app;


import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import java.io.IOException;
import java.util.Objects;

public class OAuthClientRequestInterceptor implements ClientHttpRequestInterceptor {


    private final OAuth2AuthorizedClientManager auth2AuthorizedClientManager;
    private final String registrationId;
    @Setter
    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();


    public OAuthClientRequestInterceptor(OAuth2AuthorizedClientManager auth2AuthorizedClientManager,
                                         String registrationId) {
        this.auth2AuthorizedClientManager = auth2AuthorizedClientManager;
        this.registrationId = registrationId;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            OAuth2AuthorizedClient oAuth2AuthorizedClient = auth2AuthorizedClientManager.authorize(OAuth2AuthorizeRequest.withClientRegistrationId(registrationId)
                    .principal(securityContextHolderStrategy.getContext().getAuthentication())
                    .build());

            request.getHeaders().setBearerAuth(Objects.requireNonNull(oAuth2AuthorizedClient).getAccessToken().getTokenValue());
        }

        return execution.execute(request, body);
    }
}
