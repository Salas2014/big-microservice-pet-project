package com.salas.catalogue.service.entity;

import lombok.Data;

@Data
public class Product {
    private Integer id;
    private String title;
    private String details;

    public Product() {
    }

    public Product(Integer id, String title, String details) {
        this.id = id;
        this.title = title;
        this.details = details;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
