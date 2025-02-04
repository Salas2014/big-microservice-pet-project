package com.salas.catalogue.service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
@Table(schema = "catalogue", name = "t_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_title")
    @NotNull
    @Size(min = 3, max = 50)
    private String title;

    @Column(name = "c_details")
    @Size(max = 1000)
    private String details;

    public Product() {
    }

    public Product(Integer id, String title, String details) {
        this.id = id;
        this.title = title;
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(title, product.title) && Objects.equals(details, product.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, details);
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
