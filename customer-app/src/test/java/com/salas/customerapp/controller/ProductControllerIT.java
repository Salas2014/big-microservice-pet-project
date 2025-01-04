package com.salas.customerapp.controller;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.salas.customerapp.config.TestBeans;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import wiremock.org.eclipse.jetty.http.HttpHeader;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser;

@SpringBootTest
@AutoConfigureWebTestClient
@WireMockTest(httpPort = 54321)
@Import(TestBeans.class)
class ProductControllerIT {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void addToFavourites_RequestIsValid_ReturnsRedirectionToProductPage() {

        ResponseDefinitionBuilder responseDefinitionBuilder = WireMock.okJson("""
                      {
                        "id": 1,
                        "title": "Name 1",
                        "details": "Details 1"
                      }
                """
        ).withHeader(HttpHeader.CONTENT_TYPE.asString(), MediaType.APPLICATION_JSON_VALUE);
        WireMock.stubFor(WireMock.get("/catalogue-api/products/1")
                .willReturn(responseDefinitionBuilder));

        WireMock.stubFor(WireMock.post("/feedback-api/favourite-products")
                .withRequestBody(WireMock.equalToJson("""
                        {
                        "productId": 1
                        }
                        """))
                .withHeader(HttpHeader.CONTENT_TYPE.asString(), equalTo(MediaType.APPLICATION_JSON_VALUE))
                .willReturn(created()
                        .withHeader(HttpHeader.CONTENT_TYPE.asString(), MediaType.APPLICATION_JSON_VALUE)
                        .withBody("""
                                {
                                    "id": "25d57ed0-7b3e-4d72-a8a9-d2ba2b0d1f56"
                                    "productId": 1
                                }
                                """))
        );

        webTestClient.mutateWith(mockUser())
                .post()
                .uri("/customer/products/1/add_to_favourites")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().location("/customer/products/1");

        WireMock.verify(getRequestedFor(urlPathMatching("/catalogue-api/products/1")));
        WireMock.verify(postRequestedFor(urlPathMatching("/feedback-api/favourite-products"))
                .withRequestBody(WireMock.equalToJson("""
                        {
                        "productId": 1
                        }
                        """)));
    }


}