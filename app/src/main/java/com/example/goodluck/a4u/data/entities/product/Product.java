package com.example.goodluck.a4u.data.entities.product;



public class Product {
    // TODO: 05/10/2017 Add the reminder fields

    private long id;
    private String title;
    private String description;
    private double price;

    // Getters
    public long getId() {
        return this.id;
    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
