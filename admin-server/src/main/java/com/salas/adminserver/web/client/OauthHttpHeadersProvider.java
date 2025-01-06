//package com.salas.adminserver.web.client;
//
//import de.codecentric.boot.admin.server.domain.entities.Instance;
//import de.codecentric.boot.admin.server.web.client.HttpHeadersProvider;
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
//
//public class OauthHttpHeadersProvider implements HttpHeadersProvider {
//    private final OAuth2AuthorizedClientManager manager;
//
//    public OauthHttpHeadersProvider(OAuth2AuthorizedClientManager manager) {
//        this.manager = manager;
//    }
//
//    @Override
//    public HttpHeaders getHeaders(Instance instance) {
//
//        OAuth2AuthorizedClient oAuth2AuthorizedClient = manager.authorize(OAuth2AuthorizeRequest.withClientRegistrationId("keycloak")
//                .principal("admin-service").build());
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setBearerAuth(oAuth2AuthorizedClient.getAccessToken().getTokenValue());
//
//        return httpHeaders;
//    }
//}
