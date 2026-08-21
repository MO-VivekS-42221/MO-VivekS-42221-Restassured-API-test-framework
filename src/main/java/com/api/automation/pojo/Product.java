package com.api.automation.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * Product POJO - Represents a Product object
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String category;
    private String manufacturer;
    private Boolean inStock;

    /**
     * Constructor for creating new Product (without id)
     */
    public Product(String name, String description, Double price, Integer quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
}
