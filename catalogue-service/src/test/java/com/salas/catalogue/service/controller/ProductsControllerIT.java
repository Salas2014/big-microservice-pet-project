package com.salas.catalogue.service.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class ProductsControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Test
    @Sql("/sql/products.sql")
    void findProducts() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/catalogue-api/products")
                .param("filter", "detail")
                .with(jwt().jwt(builder -> builder.claim("scope", "view_catalogue")));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                 {"id":  1, "title": "Product 1", "details":  "detail 1"},
                                 {"id":  3, "title": "Product 3", "details":  "detail 3"}
                                ]
                                """));
    }

    @Test
    void createProduct_RequestIsValid_ReturnsNewProduct() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.post("/catalogue-api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        { "title": "Product 1", "details":  "detail 1"}
                        """)
                .with(jwt().jwt(builder -> builder.claim("scope", "edit_catalogue")));


        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        header().string(HttpHeaders.LOCATION, "http://localhost/catalogue/products/1"),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                {"id":  1, "title": "Product 1", "details":  "detail 1"}
                                """));

    }

    @Test
    void createProduct_UserIsNotAuthorized() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.post("/catalogue-api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        { "title": "Product 1", "details":  "detail 1"}
                        """)
                .with(jwt().jwt(builder -> builder.claim("scope", "view_catalogue")));


        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isForbidden());
    }

}