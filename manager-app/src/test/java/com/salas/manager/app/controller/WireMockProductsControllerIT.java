package com.salas.manager.app.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.salas.manager.app.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WireMockTest(httpPort = 54321)
@ActiveProfiles(value = "wiremock")
public class WireMockProductsControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Test
    void getProductList() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/catalogue/products/list")
                .queryParam("filter", "product")
                .with(user("salas").roles("MANAGER"));


        WireMock.stubFor(WireMock.get(WireMock.urlPathMatching("/catalogue-api/products"))
                .withQueryParam("filter", WireMock.equalTo("product"))
                .willReturn(WireMock.ok("""
                        {"id" 1, "title: "1", "details" "product 1"},
                        {"id" 2, "title: "2", "details" "product 2"}
                        """).withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        view().name("catalogue/products/list"),
                        model().attribute("filter", "product"),
                        model().attribute("products", List.of(
                                new Product(1, "1", "product 1"),
                                new Product(2, "2", "product 1")
                        ))
                );
    }

}
